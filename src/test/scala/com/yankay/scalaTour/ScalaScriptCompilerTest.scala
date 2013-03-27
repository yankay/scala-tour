package com.yankay.scalaTour

import java.io.ByteArrayOutputStream
import scala.tools.nsc.ScriptRunner
import scala.tools.nsc.GenericRunnerCommand
import java.io.StringWriter
import org.scalatest.FunSuite
import org.scalatest.FlatSpec

class ScalaScriptCompilerTest extends FunSuite {
  test("ScalaScriptCompilerTest") {
    val buffer = new ByteArrayOutputStream();
    val ScalaScriptCompiler = new ScalaScriptCompiler(buffer);
    val location = ScalaScriptCompiler.compile("""println("hw")""");
    println(location)
    print(new String(buffer.toByteArray(), "UTF-8"))
    //    val command = new GenericRunnerCommand(List(), (x: String) => println(x))
    //    import command.{ settings };
    //    ScriptRunner.runCommand(settings, """println("hw")""", List("-nc"))
  }
}