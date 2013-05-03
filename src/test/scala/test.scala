
import java.util.Date
import scala.runtime.RichInt
import scala.runtime.RichInt


object test {
  def main(args: Array[String]) {

    import scala.actors.remote.RemoteActor._
    import scala.actors.Actor._
    import scala.actors.remote.Node

    implicit def strToDate(str: String) =
      new SimpleDateFormat("yyyy-MM-dd").parse(str)

    println("2013-01-01 unix time: " + "2013-01-01".getTime() / 1000l)

  }
}



