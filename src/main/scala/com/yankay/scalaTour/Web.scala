package com.yankay.scalaTour

import org.jboss.netty.handler.codec.http.{ HttpRequest, HttpResponse }
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{ Http, Response }
import com.twitter.finagle.Service
import com.twitter.util.Future
import java.net.InetSocketAddress
import util.Properties
import java.io.ByteArrayOutputStream

object Web {
  def main(args: Array[String]) {
    val port = Properties.envOrElse("PORT", "8080").toInt
    println("Starting on port:" + port)
    ServerBuilder()
      .codec(Http())
      .name("hello-server")
      .bindTo(new InetSocketAddress(port))
      .build(new Hello)
    println("Started.")
  }
}

class Hello extends Service[HttpRequest, HttpResponse] {
  def apply(req: HttpRequest): Future[HttpResponse] = {
    val response = Response()
    response.setStatusCode(200)
    val buffer = new ByteArrayOutputStream();
    val location = ScalaScriptCompiler.compile("""println("hw")""", buffer);
    println("compiled location:" + location);
    ScalaScriptProcess.create(location.get, buffer, buffer).get.run().exitValue;
    location.get.deleteIfExists();
    println("runed");
    response.setContentString(buffer.toString())
    Future(response)
  }
}