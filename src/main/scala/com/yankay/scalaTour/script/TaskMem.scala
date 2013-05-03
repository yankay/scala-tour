package com.yankay.scalaTour.script

import java.util.Date
import scala.actors.Actor._
import scala.actors.TIMEOUT
import org.apache.commons.logging.LogFactory

object CompileStatus extends Enumeration {
  type T = Value
  val Perpared, Compileing, Compiled = Value
}

object RunStatus extends Enumeration {
  type T = Value
  val Perpared, Running, Runned = Value
}

case class Task(val taskId: Int, val script: String,
  val compiledPath: Option[String], val compileLog: List[String], val compileStatus: CompileStatus.T,
  val stdOut: List[String], val stdErr: List[String],
  val runStart: Option[Date], val runEnd: Option[Date],
  val runStatus: RunStatus.T, val runCode: Option[Int],
  val expireAt: Date)

object TaskMem {

  val logger = LogFactory.getLog("TaskMem")

  var tasks = List[Task]()

  def getById(id: Int): Option[Task] = {
    TaskMem.synchronized(
      tasks.find(_.taskId == id))
  }

  def getByScript(script: String): Option[Task] = {
    TaskMem.synchronized(
      tasks.find(_.script == script))
  }

  def putAndReplace(task: Task) = {
    logger.debug("putAndReplace " + task)
    TaskMem.synchronized(
      tasks = task :: tasks.filterNot(_.taskId == task.taskId))
  }

  actor {
    loop {
      reactWithin(60 * 1000) {
        case TIMEOUT => removeExpire
      }
    }
  }

  def removeExpire() = {
    logger.debug("removeExpire")
    TaskMem.synchronized(
      tasks = tasks.filter(_.expireAt.after(new Date())))
  }

  var id = 0

  def generateId(): Int = {
    TaskMem.synchronized {
      id = id + 1;
      return id
    }
  }

}