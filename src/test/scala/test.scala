

import scala.util.matching.Regex

object test {
  def main(args: Array[String]) {
    import scala.collection.mutable.WeakHashMap

    val cache = new WeakHashMap[Int, Int]
    def memo(f: Int => Int) = (x: Int) => cache.getOrElseUpdate(x, f(x))

    def fibonacci_(in: Int): Int = in match {
      case 0 => 0;
      case 1 => 1;
      case n: Int => memo(fibonacci_)(n - 1) + memo(fibonacci_)(n - 2)
    }

    val fibonacci: Int => Int = memo(fibonacci_)

    val t1 = System.currentTimeMillis()
    println(fibonacci(40))
    println("it takes " + (System.currentTimeMillis() - t1) + "ms")

    val t2 = System.currentTimeMillis()
    println(fibonacci(40))
    println("it takes " + (System.currentTimeMillis() - t2) + "ms")

  }

}



