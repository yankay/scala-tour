package com.yankay.scalaTour.script

import java.util.Date

object TaskMemTest {
  def main(args: Array[String]) {

  }
  val t = Task(1, "xx",
    None, List[String](), CompileStatus.Perpared,
    List(), List(), None, None, RunStatus.Perpared, None, new Date())

  TaskMem.putAndReplace(t)
  
  println(TaskMem.getById(1))
}