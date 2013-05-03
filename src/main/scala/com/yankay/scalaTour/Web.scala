package com.yankay.scalaTour

import java.io.ByteArrayOutputStream
import scala.actors.Actor
import scala.actors.TIMEOUT
import scala.reflect.io.File
import scala.util.Properties
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.json4s.jackson.JsonMethods
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.server.handler.ResourceHandler
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.FilterHolder
import org.eclipse.jetty.server.handler.GzipHandler
import org.eclipse.jetty.servlets.GzipFilter
import java.util.EnumSet
import javax.servlet.DispatcherType
import scala.collection.mutable.WeakHashMap

object Web {
  val cache = new WeakHashMap[String, RunResponse]

  def handler(): Handler = {
    val context = new ServletContextHandler
    context.setContextPath("/");
    context.setResourceBase("webapp")
    context.addServlet(new ServletHolder(new RunServlet()), "/run");
    context.addServlet(new ServletHolder(new DefaultServlet()), "/");
    val fh = new FilterHolder(new GzipFilter())
    fh.setInitParameter("mimeTypes", "text/html,text/css,application/x-javascript,imgage/png")
    context.addFilter(fh, "/*", EnumSet.of(DispatcherType.FORWARD,
      DispatcherType.INCLUDE,
      DispatcherType.REQUEST,
      DispatcherType.ASYNC,
      DispatcherType.ERROR))
    context
  }

  def main(args: Array[String]) {

    val server = new Server(Properties.envOrElse("PORT", "8080").toInt);
    server.setHandler(handler())
    println("Start")
    server.start()
  }
}

class RunServlet extends HttpServlet {

  def compileAndRun(code: String): RunResponse = {
    //    println("code:" + code)
    val buffer = new ByteArrayOutputStream();
    val file = ScalaScriptCompiler.compile(code, buffer);
    val error = new String(buffer.toByteArray()).lines.toList
    //    println(new String(buffer.toByteArray()))
    //    println(error);
    val errorMsg = error.map(x => x.replaceAll("/tmp/scala-script.*scala", "main.scala"))

    file match {
      case Some(f) => {
        try {
          val (outEvents, errEvents) = run(f);
          new RunResponse(errorMsg.toList, outEvents, errEvents)
        } finally {
          f.deleteIfExists();
        }
      }
      case _ => new RunResponse(errorMsg, List(), List())
    }
  }

  def run(file: File): (List[String], List[String]) = {
    val out = new ByteArrayOutputStream();
    val err = new ByteArrayOutputStream();
    val proc = ScalaScriptProcess.create(file, out, err);
    proc match {
      case Some(p) => {
        val pid = p.run();
        val timeout = new TimeoutActor(pid, 10*1000)
        timeout.start
        val existValue = pid.exitValue()
        timeout ! existValue
        val outEvents = new String(out.toByteArray()).lines.toList
        val errEvents = new String(err.toByteArray()).lines.toList.filterNot(_.startsWith("Picked up JAVA_TOOL_OPTIONS"))
        existValue match {
          case 0 => (outEvents, errEvents)
          case x => (outEvents, errEvents ::: List("exit value is " + x))
        }

      }
      case _ => (List(), List())
    }
  }

  class TimeoutActor(proc: scala.sys.process.Process, timeout: Long) extends Actor {
    def act() {
      receiveWithin(timeout) {
        case Int =>
        case TIMEOUT => proc.destroy()
      }
    }
  }

  def json(mode: RunResponse): String = {
    import org.json4s._
    import org.json4s.JsonDSL._
    val json = ("Errors" -> mode.errors) ~ ("Events" -> mode.events) ~ ("ErrEvents" -> mode.errEvents)
    JsonMethods.pretty(JsonMethods.render(json))
  }

  def memo(f: String => RunResponse) = {
    (x: String) => Web.cache.getOrElseUpdate(x, f(x))
  }
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) = {
    val code = req.getParameter("code")
    if (code == null)
      resp.setStatus(404)
    else {
      var f = memo(compileAndRun)
      val model = f(code)
      resp.getWriter().print(json(model))
      resp.getWriter().flush()
      resp.setStatus(200)
    }
  }

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) = {
    doGet(req, resp)
  }
}

case class RunResponse(errors: List[String], events: List[String], errEvents: List[String]) {

}