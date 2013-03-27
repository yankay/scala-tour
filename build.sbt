import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

net.virtualvoid.sbt.graph.Plugin.graphSettings

name := "scala-tour"

version := "1.0"

scalaVersion := "2.10.1"

resolvers += "scalatest repository" at "http://oss.sonatype.org/content/groups/public/org/scalatest/"


libraryDependencies += "com.twitter" % "finagle-core_2.10" % "6.2.0"

libraryDependencies += "com.twitter" % "finagle-http_2.10" % "6.2.0"

libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.10.1"

libraryDependencies += "com.google.guava" % "guava" % "14.0-rc3"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"