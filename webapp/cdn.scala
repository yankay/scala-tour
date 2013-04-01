object CDN {
  def main(args: Array[String]) {
    val url = args(0)
    val cdn = scala.collection.mutable.Map[String, String]()
    cdn += ("""href="css/""" -> ("href=\"" + url + "css/").toString())
    cdn += ("""src="js/""" -> ("src=\"" + url + "js/").toString())
    cdn += ("""src="img/""" -> ("src=\"" + url + "img/").toString())
    scala.io.Source.stdin.getLines.foreach(
      x => {
        var line = x
        cdn.foreach(p => line = line.replace(p._1, p._2))
        println(line)
      })
  }
}