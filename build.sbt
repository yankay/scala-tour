import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

net.virtualvoid.sbt.graph.Plugin.graphSettings

name := "scala-tour"

version := "1.0"

scalaVersion := "2.10.1"

libraryDependencies += "org.scala-lang" % "scala-actors" % "2.10.1"
            
libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.10.1"

libraryDependencies += "com.google.guava" % "guava" % "14.0-rc3"

libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "9.0.0.v20130308"

libraryDependencies += "org.eclipse.jetty" % "jetty-servlet" % "9.0.0.v20130308"

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.2"