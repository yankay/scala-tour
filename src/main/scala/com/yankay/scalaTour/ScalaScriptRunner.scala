package com.yankay.scalaTour

import java.io.OutputStream
import java.io.PrintStream
import java.io.PrintWriter
import scala.collection.mutable.StringBuilder
import scala.reflect.io.Directory
import scala.reflect.io.File
import scala.reflect.io.Path
import scala.reflect.io.Path.string2path
import scala.sys.process.Process
import scala.sys.process.ProcessBuilder
import scala.sys.process.ProcessCreation
import scala.sys.process.ProcessIO
import scala.sys.process.ProcessLogger
import scala.tools.nsc.GenericRunnerCommand
import scala.tools.nsc.GenericRunnerSettings
import scala.tools.nsc.Global
import scala.tools.nsc.Settings
import scala.tools.nsc.reporters.ConsoleReporter
import scala.tools.nsc.reporters.Reporter
import scala.tools.nsc.util.HasClassPath
import scala.util.Properties
import scala.tools.nsc.io.Jar

object ScalaScriptCompiler {
  /** Default name to use for the wrapped script */
  val defaultScriptMain = "Main"

  /** Pick a main object name from the specified settings */
  def scriptMain(settings: Settings) = settings.script.value match {
    case "" => defaultScriptMain
    case x => x
  }

  val settings: GenericRunnerSettings = {
    val command = new GenericRunnerCommand(List(), Console.err println _)
    val CP = Properties.propOrEmpty("java.class.path")
    val PS = Properties.propOrEmpty("path.separator")
    CP.split(PS).foreach((x: String) => {
      command.settings.classpath.append(x)
      command.settings.bootclasspath.append(x)
    })
    command.settings.nc.value = true
    command.settings.feature.value = true
    command.settings.language.appendToValue("reflectiveCalls")
    command.settings.language.appendToValue("implicitConversions")
    command.settings;
  }

  def compile(script: String, err: OutputStream): Option[File] = {
    val scriptFile = File.makeTemp("scala-script", ".scala")
    // save the command to the file
    val scriptheader =  """import scala.io.Codec
    import java.nio.charset.CodingErrorAction

    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)
    """
    scriptFile.writeAll(scriptheader+script)
    try compile(scriptFile, err)
    finally scriptFile.delete() // in case there was a compilation error
  }

  def compile(
    scriptFile: File, err: OutputStream): Option[File] = {
    def mainClass = scriptMain(settings)
    val compiledPath = Directory makeTemp "scalascript"

    // delete the directory after the user code has finished
    //    sys.addShutdownHook(compiledPath.deleteRecursively())

    settings.outdir.value = compiledPath.path

    /**
     * Setting settings.script.value informs the compiler this is not a
     *  self contained compilation unit.
     */
    settings.script.value = mainClass
    val reporter = new ConsoleReporter(settings, Console.in, new PrintWriter(err, true))
    val compiler = Global(settings, reporter)

    new compiler.Run compile List(scriptFile.path)
    if (reporter.hasErrors) None else
      try createjar(compiledPath)
      finally compiledPath.deleteRecursively;
  }

  def createjar(compiledPath: Directory): Option[File] = {
    val jarpath = File.makeTemp("scalascript", ".jar")
    jarpath.deleteIfExists()
    try {
      Jar.create(jarpath, compiledPath, defaultScriptMain)
      Some(jarpath)
    } catch { case _: Exception => jarpath.delete(); None }

  }
}

object ScalaScriptProcess {
  def javaf(): String = {
    val head = Properties.propOrEmpty("java.home") + File.separator + "bin" + File.separator + "java"
    if (File.separator == "/")
      head + ""
    else
      head + ".exe"
  }

  def create(compiledLocation: File, out: OutputStream, err: OutputStream): Option[ScalaScriptProcess] = {

    val CP = Properties.propOrEmpty("java.class.path") + Properties.propOrEmpty("path.separator") + compiledLocation
    var args = List("-cp", CP, "Main")
    if (Path(javaf()).exists) {
      val outp = new PrintStream(out, true);
      val errp = new PrintStream(err, true);
      val pl = ProcessLogger(
        outp println _,
        errp println _)
      Some(new ScalaScriptProcess(Process(javaf(), args), pl))
    } else None

  }
}

class ScalaScriptProcess(val builder: ProcessBuilder, val logger: ProcessLogger) {
  def run(): Process = {
    builder.run(logger);
  }
}
