import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

net.virtualvoid.sbt.graph.Plugin.graphSettings

name := "scala-tour"

version := "1.0"

scalaVersion := "2.10.1"

libraryDependencies ++= Seq(
	"org.scala-lang" % "scala-actors" % "2.10.1", 
	"org.scala-lang" % "scala-compiler" % "2.10.1",
    "com.typesafe.akka" %% "akka-testkit" % "2.1.2",
	"com.typesafe.akka" %% "akka-actor" % "2.1.2",
    "com.typesafe.akka" %% "akka-remote" % "2.1.2",
	"org.eclipse.jetty" % "jetty-server" % "8.1.10.v20130312",
	"org.eclipse.jetty" % "jetty-servlet" % "8.1.10.v20130312",
	"org.eclipse.jetty" % "jetty-servlets" % "8.1.10.v20130312",
	"org.json4s" %% "json4s-jackson" % "3.2.2",
	"commons-beanutils" % "commons-beanutils" % "1.8.3",
	"org.specs2" % "specs2_2.10" % "1.14"
)