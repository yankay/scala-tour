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

object Web {

  def handler(): Handler = {
    val context = new ServletContextHandler
    context.setContextPath("/");
    context.addServlet(new ServletHolder(new RunServlet()), "/run");
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
    file match {
      case Some(f) => {
        val events = run(f)
        new RunResponse(error.toList, events)
      }
      case _ => new RunResponse(error, List())
    }
  }

  def run(file: File): List[String] = {
    val out = new ByteArrayOutputStream();
    val err = new ByteArrayOutputStream();
    val proc = ScalaScriptProcess.create(file, out, err);
    proc match {
      case Some(p) => {
        val pid = p.run();
        val timeout = new TimeoutActor(pid, 5000)
        timeout.start
        val existValue = pid.exitValue()
        timeout ! existValue
        val outEvents = new String(out.toByteArray()).lines.toList
        val errEvents = new String(err.toByteArray()).lines.toList
        val events = outEvents ::: errEvents
        existValue match {
          case 0 => events.toList
          case x => events ::: List("exit value is " + x)
        }

      }
      case _ => List()
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
    val json = ("Errors" -> mode.errors) ~ ("Events" -> mode.events)
    JsonMethods.pretty(JsonMethods.render(json))
  }

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) = {
    val code = req.getParameter("code")
    if (code == null)
      resp.setStatus(404)
    else {
      val model = compileAndRun(code)
      resp.getWriter().print(json(model))
      resp.getWriter().flush()
      resp.setStatus(200)
    }
  }

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) = {
    doGet(req, resp)
  }
}

case class RunResponse(errors: List[String], events: List[String]) {

}