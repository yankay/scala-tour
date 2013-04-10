object CDN {
  val translate = scala.collection.mutable.Map[String, String]()
  translate += (" Tour" -> " 指南")
  translate += ("Welcome to Scala programming lanaguage" -> "欢迎来到Scala语言")
  translate += ("Glance" -> "概览")
  translate += ("About" -> "关于")

  def main(args: Array[String]) {
    val lang = "en"
      args(0)

    scala.io.Source.stdin.getLines.foreach(
      x => {
        var line = x
        translate.foreach(p => line = line.replace(p._1, p._2))
        println(line)
      })
  }
}