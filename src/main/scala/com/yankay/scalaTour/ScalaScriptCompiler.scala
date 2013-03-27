package com.yankay.scalaTour

import java.io.OutputStream
import java.io.PrintStream
import java.io.PrintWriter
import scala.collection.mutable.StringBuilder
import scala.reflect.io.Directory
import scala.reflect.io.File
import scala.reflect.io.Path
import scala.reflect.io.Path.string2path
import scala.tools.nsc.CompileSocket
import scala.tools.nsc.GenericRunnerCommand
import scala.tools.nsc.GenericRunnerSettings
import scala.tools.nsc.Global
import scala.tools.nsc.Settings
import scala.tools.nsc.io.Socket
import scala.tools.nsc.reporters.ConsoleReporter
import scala.tools.nsc.reporters.Reporter
import scala.tools.nsc.util.HasClassPath
import java.net.URLClassLoader

class ScalaScriptCompiler(err: OutputStream) {

  def errorFn(str: String): Unit = {
    new PrintStream(err, true) println str
  }

  def compile(script: String): Option[Directory] = {
    val command = new GenericRunnerCommand(List(), (x: String) => errorFn(x))
    import command.{ settings };
    System.getProperties().getProperty("java.class.path").
      split(System.getProperties().getProperty("path.separator")).foreach((x: String) => {
        settings.classpath.append(x)
        settings.bootclasspath.append(x)
      })

    println(settings.classpath)
    println(settings.bootclasspath)
    settings.nc.value=true
    val scriptFile = File.makeTemp("scalacmd", ".scala")
    // save the command to the file
    scriptFile writeAll script
    val compiledLocation: StringBuilder = new StringBuilder()
    try compile(settings, scriptFile.path)
    finally scriptFile.delete() // in case there was a compilation error
  }

  lazy val compileSocket = CompileSocket

  /** Default name to use for the wrapped script */
  val defaultScriptMain = "Main"

  /** Pick a main object name from the specified settings */
  def scriptMain(settings: Settings) = settings.script.value match {
    case "" => defaultScriptMain
    case x => x
  }

  def isScript(settings: Settings) = settings.script.value != ""

  /** Choose a jar filename to hold the compiled version of a script. */
  private def jarFileFor(scriptFile: String) = File(
    if (scriptFile endsWith ".jar") scriptFile
    else scriptFile.stripSuffix(".scala") + ".jar")

  /** Read the entire contents of a file as a String. */
  private def contentsOfFile(filename: String) = File(filename).slurp()

  /**
   * Split a fully qualified object name into a
   *  package and an unqualified object name
   */
  private def splitObjectName(fullname: String): (Option[String], String) =
    (fullname lastIndexOf '.') match {
      case -1 => (None, fullname)
      case idx => (Some(fullname take idx), fullname drop (idx + 1))
    }

  /**
   * Compile a script using the fsc compilation daemon.
   */
  private def compileWithDaemon(settings: GenericRunnerSettings, scriptFileIn: String) = {
    val scriptFile = Path(scriptFileIn).toAbsolute.path
    val compSettingNames = new Settings(sys.error).visibleSettings.toList map (_.name)
    val compSettings = settings.visibleSettings.toList filter (compSettingNames contains _.name)
    val coreCompArgs = compSettings flatMap (_.unparse)
    val compArgs = coreCompArgs ++ List("-Xscript", scriptMain(settings), scriptFile)

    CompileSocket getOrCreateSocket "" match {
      case Some(sock) => compileOnServer(sock, compArgs)
      case _ => false
    }
  }

  protected def newGlobal(settings: Settings, reporter: Reporter) =
    Global(settings, reporter)

  /**
   * Compiles the script file, and returns the directory with the compiled
   *  class files, if the compilation succeeded.
   */
  def compile(
    settings: GenericRunnerSettings,
    scriptFile: String): Option[Directory] = {
    def mainClass = scriptMain(settings)
    val compiledPath = Directory makeTemp "scalascript"

    // delete the directory after the user code has finished
    sys.addShutdownHook(compiledPath.deleteRecursively())

    settings.outdir.value = compiledPath.path

    if (settings.nc.value) {
      /**
       * Setting settings.script.value informs the compiler this is not a
       *  self contained compilation unit.
       */
      settings.script.value = mainClass
      val reporter = new ConsoleReporter(settings, Console.in, new PrintWriter(err, true))
      val compiler = newGlobal(settings, reporter)

      new compiler.Run compile List(scriptFile)
      if (reporter.hasErrors) None else Some(compiledPath)
    } else if (compileWithDaemon(settings, scriptFile)) Some(compiledPath)
    else None
  }

  // This is kind of a suboptimal way to identify error situations.
  val errorMarkers = Set("error:", "error found", "errors found", "bad option")
  def isErrorMessage(msg: String) = errorMarkers exists (msg contains _)

  def compileOnServer(sock: Socket, args: Seq[String]): Boolean = {
    var noErrors = true

    sock.applyReaderAndWriter { (in, out) =>
      out println (compileSocket getPassword sock.getPort())
      out println (args mkString "\0")

      def loop(): Boolean = in.readLine() match {
        case null => noErrors
        case line =>
          if (isErrorMessage(line))
            noErrors = false

          errorFn(line)
          loop()
      }
      try loop()
      finally sock.close()
    }
  }

}