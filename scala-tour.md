# Scala Tour

[http://www.scala-tour.com/](http://www.scala-tour.com/) 

## Basic

### Expressions and Values

In Scala, almost everything is an expression.println("hello wolrd")is an expression, "hello"+" world"
is also an expression.

Constants can be created with val, and variables can be created with var. More constants are better.

```
var helloWorld = "hello" + " world" 
println(helloWorld)

val again = " again" 
helloWorld = helloWorld + again
println(helloWorld)
```

### First class Functions

You can create functions with def. And the function body is an expression.

When the body is a block expression, it returns the value of the last line. So there's no need to use the return keyword

And like values, functions can also be assigned using var or val So it can be passed as an argument to another function.

```
def square(a: Int) = a * a

def squareWithBlock(a: Int) = {
	a * a
}

val squareVal = (a: Int) => a * a

def addOne(f: Int => Int, arg: Int) = f(arg) + 1

println("square(2):" + square(2))
println("squareWithBlock(2):" + squareWithBlock(2))
println("squareVal(2):" + squareVal(2))
println("addOne(squareVal,2):" + addOne(squareVal, 2))
```



### Loan Pattern

For functions which can be passed as arguments, the 'Loan' pattern is easy to implement.

This example reads the self pid from /proc/self/stat.

Because the 'withScanner' function encapsulates the 'try-finally' block,there's no need to call 'close()' any more.

PS: the expression's return type is 'Unit' when it doesn't return a value.

```
import scala.reflect.io.File
import java.util.Scanner

def withScanner(f: File, op: Scanner => Unit) = {
	val scanner = new Scanner(f.bufferedReader)
	try {
		op(scanner)
	} finally {
		scanner.close()
	}
}

withScanner(File("/proc/self/stat"),
	scanner => println("pid is " + scanner.next()))
```


### Call-by-Name

This example shows the call by name, When the last line tries to calculate '1 / 0', the program will throw an exception.

Try to change 'def log(msg: String)' to 'def log(msg: => String)'.The program will not throw an exception because it has been changed to call-by-name


Call-by-name means that the argument will be calculated when it be actually called. Because 'logEnable = false', the '1 / 0' would be skipped.


Call-by-name can reduce the useless calculation and exception.


```
val logEnable = false

def log(msg: String) =
	if (logEnable) println(msg)

val MSG = "programing is running"

log(MSG + 1 / 0)
```

### Define Class

The 'class' keyword defines a class, and the 'new' keyword creates an instance.
The fields can be also defined in class, like the 'firstName' and 'lastName'.These are automatically generated from the constructor's arguments.
Methods can be defined with def, and fields can be defined with val or var
The function name can be any characters like +,-,*,/.
The 'obama.age_=(51)' can be simplified as 'obama.age = 51'.
And the 'obama.age()' can be simplified as 'obama.age'.

```
class Person(val firstName: String, val lastName: String) {

	private var _age = 0
	def age = _age
	def age_=(newAge: Int) = _age = newAge

	def fullName() = firstName + " " + lastName

	override def toString() = fullName()
}

val obama: Person = new Person("Barack", "Obama")

println("Person: " + obama)
println("firstName: " + obama.firstName)
println("lastName: " + obama.lastName)
obama.age_=(51)
println("age: " + obama.age())
```


### Duck Typing

When I see a bird that walks like a duck and swims like a duck and quacks like a duck, I call that bird a duck.
This example uses '{ def close(): Unit }' as the type of argument. So any class contains methods 'close()' can be passed.
And there's no need to use 'inherit'。

```
def withClose(closeAble: { def close(): Unit }, op: { def close(): Unit } => Unit) {
	try {
		op(closeAble)
	} finally {
		closeAble.close()
	}
}

class Connection {
	def close() = println("close Connection")
}

val conn: Connection = new Connection()
withClose(conn, conn =>
	println("do something with Connection"))
```


### Currying 

This example is similar to the previous one.The difference between them is this one leverage currying technology。
def add(x:Int, y:Int) = x + y is a normal function
def add(x:Int) = (y:Int) => x + y is a curried function.The return value is a function expression.
def add(x:Int)(y:Int) = x + y is syntactic sugar
Currying can let our code look like it is part of the language.
Change the withclose(...)(...) to withclose(...){...}
Is it similar with Java's synchronized block?

```
def withClose(closeAble: { def close(): Unit })(op: { def close(): Unit } => Unit) {
	try {
		op(closeAble)
	} finally {
		closeAble.close()
	}
}

class Connection {
	def close() = println("close Connection")
}

val conn: Connection = new Connection()
withClose(conn)(conn =>
	println("do something with Connection"))
```


### Generic

The sample before can be more simplified with generics.
Try to change val msg = "123456" to val msg = 123456.
Although the type of msg changed from String to Int, the program still compiles.

```
def withClose[A <: { def close(): Unit }, B](closeAble: A)(op: A => B) {
	try {
		op(closeAble)
	} finally {
		closeAble.close()
	}
}

class Connection {
	val msg = "123456"
	def close() = println("close Connection")
}

val conn: Connection = new Connection()
val msg = withClose(conn) { conn =>
	{
		println("do something with Connection")
		conn.msg
	}
}
	
println(msg)
```

### Traits

Traits look like Java's interfaces, but with function blocks. One class can extend several traits using the with keyword.
This example extends java.util.ArrayList with a foreach loop.
Try to append with JsonAble to extend the ability of toJson.

```
trait ForEachAble[A] {
	def iterator: java.util.Iterator[A]
	def foreach(f: A => Unit) = {
		val iter = iterator
		while (iter.hasNext)
			f(iter.next)
	}
}

trait JsonAble {
	override def toString() =
		scala.util.parsing.json.JSONFormat.defaultFormatter(this)
}

val list = new java.util.ArrayList[Int]() with ForEachAble[Int]
list.add(1); list.add(2)

list.foreach(x => println(x))
println("Json: " + list.toString())
```


## Functional Programing

### Pattern Matching

Pattern Matching is more flexible than switch-case and simpler than if-else.
This example shows a Fibonacci function implemented with pattern matching.
The case keyword matches on a value. The case _ means it can match anything.
But the example has a bug. If the input is a negative number, it will loop endlessly.
Try to add if after case. Change case n: Int to case n: Int if (n > 1).
Try to add case n: String => fibonacci(n.toInt) before case _ to match String type.
Try to add println(fibonacci(-3))；println(fibonacci("3")) to test the program. 


```
def fibonacci(in: Any): Int = in match {
	case 0 => 0
	case 1 => 1
	case n: Int => fibonacci(n - 1) + fibonacci(n - 2)
	case _ => 0
}

println(fibonacci(3))
```

### Case Class

Case classes are used to conveniently store and match on the contents of a class.
You can construct them without using new.
It also has hashcode, equality and nice toString methods.
Try to append println(Sum(1,2)) last.
Because of the require(n >= 0), it will throw an exception when the input is negative.


```
abstract class Expr

case class FibonacciExpr(n: Int) extends Expr {
  require(n >= 0)
}

case class SumExpr(a: Expr, b: Expr) extends Expr

def value(in: Expr): Int = in match {
  case FibonacciExpr(0) => 0
  case FibonacciExpr(1) => 1
  case FibonacciExpr(n) => value(SumExpr(FibonacciExpr(n - 1), FibonacciExpr(n - 2)))
  case SumExpr(a, b) => value(a) + value(b)
  case _ => 0
}
println(value(FibonacciExpr(3)))
```

### The power of functional programing

This example determines whether there is an odd number in the list.
Every line of the code excluding the last line is created using imperative programming.The last line is created using functional programming.
Treating function expression as function arguments can simplify code effectively.The _ % 2 == 1 is the syntactic sugar of (x: Int) => x % 2 == 1.
Ruby's power is from magic, but Scala's power is from science.

```
val list = List(1, 2, 3, 4)

def containsOdd(list: List[Int]): Boolean = {
  for (i <- list) {
    if (i % 2 == 1)
      return true;
  }
  return false;
}

println("list containsOdd by for loop:" + containsOdd(list))

println("list containsOdd by funtional:" + list.exists(_ % 2 == 1))
```

### The true power of functional programming

Besides simplifying code, functional programming is more concerned with Input & Output without side-effects.
Like the Unix pipeline, simple commands can be combined together.
The filter method in List can accept a filter function to return a new List.
If you like the way Unix pipelines commands, you may also like functional programming
This example uses Scala code to simulate the Unix command line "cat file | grep 'warn' | grep '2013' | wc."

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

println("cat file | grep 'warn' | grep '2013' | wc : " 
    + file.filter(_.contains("warn")).filter(_.contains("2013")).size)
```
### Word Count
Word Count is a classic use case for Map Reduce. Map Reduce with functional programming is also a wonderful way to implement word count.
The example shows two important functions 'map' and 'reduceLeft' in List.
The map function accepts a translation expression and returns the translated list.
The reduceLeft function accepts a combining expression and returns the combined result.The first argument is the reduced value, and the second argument is the value next.
Try to change reduceLeft(_ + _) into foldLeft(0)(_ + _).foldLeft is more popular than reduceLeft for it can provide a initial value.
Map and ReduceLeft can replace a for-loop expression, making code cleaner.

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

def wordcount(str: String): Int = str.split(" ").count("msg" == _)
  
val num = file.map(wordcount).reduceLeft(_ + _)

println("wordcount:" + num)
```
### Tail Recursion

This example shows how to implement foldLeft with Tail RecursionTail Recursion is one type of Recursion, in which a function calls itself as its last expression.
Lists can be pattern matched by '::', the first element returned is head, and the others are the tail.
Tail Recursion can be optimized at compile time. So there's no need to worry about stack overflow.

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

def wordcount(str: String): Int = str.split(" ").count("msg" == _)

def foldLeft(list: List[Int])(init: Int)(f: (Int, Int) => Int): Int = {
  list match {
    case List() => init
    case head :: tail => foldLeft(tail)(f(init, head))(f)
  }
}

val num = foldLeft(file.map(wordcount))(0)(_ + _)

println("wordcount:" + num)
```

### Powerful For Expression

Loop expression is feature of imperative programming.So Scala improved it to suit functional programming.
For expressions in Scala can also return a List. With 'yield' in the loop, the value after yield will be appended to the List.
This example replaces the map function with for loop.

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

def wordcount(str: String): Int = str.split(" ").count("msg" == _)

val counts =
  for (line <- file)
    yield wordcount(line)

val num = counts.reduceLeft(_ + _)

println("wordcount:" + num)
```
### Option

NullException is the most common exception in Java. The only way to avoid it is to check null everywhere.Scala provides an Option feature to solve it.
This example creates a getProperty function, which returns Option instead of null.
We don't need to check null, getting a value from Option is enough.
Using pattern matching is a common way to get the value of Option.    Use getOrElse() to set a default value when Option is None.
Another important think is that Option has lots of functions in List, so it can work like a list in most of time.
Try to add 'osName.foreach(print _)' at last.


```

def getProperty(name: String): Option[String] = {
  val value  = System.getProperty(name)
  if (value != null) Some(value) else None
}

val osName = getProperty("os.name")

osName match {
  case Some(value) => println(value)
  case _ => println("none")
}

println(osName.getOrElse("none"))

  

```


### Lazy

Lazy is lazy initial value. A field with the lazy keyword is only initialized when it is first accessed.
This example is to get the Scala Version Code from Github. It takes a little time because of network latency.
It waste of time that if we get it with latency but we do not use it later.
So we can get it with lazy.

```
class ScalaCurrentVersion(val url: String) {
  lazy val source= {
    println("fetching from url...")
    scala.io.Source.fromURL(url).getLines().toList
  }
  lazy val majorVersion = source.find(_.contains("version.major"))
  lazy val minorVersion = source.find(_.contains("version.minor"))
}
val version = new ScalaCurrentVersion("https://raw.github.com/scala/scala/master/build.number")
println("get scala version from " + version.url)
version.majorVersion.foreach(println _)
version.minorVersion.foreach(println _)
```


## Concurrent

### Using Actor

Actors are one of Scala's concurrent models.Users of Scala earlier than version 2.10 must install [http://akka.io/](Akka).
An Actor is a like a thread instance with a mailbox.
It can be created with system.actorOf: use receive to get a message and ! to send a message.
This example is an EchoServer which can receive messages then print them.

```
import akka.actor.{ Actor, ActorSystem, Props }

val system = ActorSystem()

class EchoServer extends Actor {
  def receive = {
    case msg: String => println("echo " + msg)
  }
}

val echoServer = system.actorOf(Props[EchoServer])
echoServer ! "hi"

system.shutdown
```

### Simplify Actor

There is a simpler way to define an Actor.
Import the actor function from akka.actor.ActorDSL.
This function accepts an Actor instance, and returns a started Actor.


```
import akka.actor.ActorDSL._
import akka.actor.ActorSystem

implicit val system = ActorSystem()

val echoServer = actor(new Act {
  become {
    case msg => println("echo " + msg)
  }
})
echoServer ! "hi"
system.shutdown
```
### Actor Implementation
An Actor is more lightweight than a thread. Millions of actors can be generated in Scala. The secret is that an Actor can reuse a thread.
The mapping relationship between an Actor and a Thread is decided by a Dispatcher.
This example creates 4 Actors, and prints its thread name when invoked.
You will find there is no fixed mapping relationship between Actors and Threads.
An Actor can use many threads. And a thread can be used by many Actors.

```
import akka.actor.{ Actor, Props, ActorSystem }
import akka.testkit.CallingThreadDispatcher

implicit val system = ActorSystem()

class EchoServer(name: String) extends Actor {
  def receive = {
    case msg => println("server" + name + " echo " + msg +
      " by " + Thread.currentThread().getName())
  }
}

val echoServers = (1 to 10).map(x =>
  system.actorOf(Props(new EchoServer(x.toString))
    .withDispatcher(CallingThreadDispatcher.Id)))
(1 to 10).foreach(msg =>
  echoServers(scala.util.Random.nextInt(10)) ! msg.toString)

system.shutdown
```



### Synchronized Return

Actors are very suitable for long-running operations, like getting resources over a network.
This example creates a Future with the ask function.
In the actor we use 'sender !' to return the value.
Like Option, Future has lots of functions. The result can be printed with a foreach.


```
import akka.actor.ActorDSL._
import akka.pattern.ask

implicit val ec = scala.concurrent.ExecutionContext.Implicits.global
implicit val system = akka.actor.ActorSystem()

val versionUrl = "https://raw.github.com/scala/scala/master/starr.number"

val fromURL = actor(new Act {
  become {
    case url: String => sender ! scala.io.Source.fromURL(url)
      .getLines().mkString("\n")
  }
})

val version = fromURL.ask(versionUrl)(akka.util.Timeout(5 * 1000))
version.foreach(println _)
  
system.shutdown
```

### Asynchronous Return

Asynchronous operations can provide better performance. A Future in Scala is very powerful, it can execute asynchronously.
The Future will call the 'onComplete' function when it is finished.
It can also set a TIMEOUT when specified.

```
import akka.actor.ActorDSL._
import akka.pattern.ask

implicit val ec = scala.concurrent.ExecutionContext.Implicits.global
implicit val system = akka.actor.ActorSystem()

val versionUrl = "https://raw.github.com/scala/scala/master/starr.number"

val fromURL = actor(new Act {
  become {
    case url: String => sender ! scala.io.Source.fromURL(url)
      .getLines().mkString("\n")
  }
})

val version = fromURL.ask(versionUrl)(akka.util.Timeout(5 * 1000))
version onComplete {
  case msg => println(msg); system.shutdown
}
```
### Concurrent Collection

This example prints the time needed to access several URLs.
If we access them concurrently, the performance can be better.
Try to change the 'urls.map' to 'urls.par.map'.Now, the functions in map will run concurrently.
It's exciting to combine functional and concurrent programming!

```
import scala.io.Codec
import java.nio.charset.CodingErrorAction

implicit val codec = Codec("UTF-8")
codec.onMalformedInput(CodingErrorAction.REPLACE)
codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

val urls = "http://scala-lang.org" :: "https://github.com/yankay/scala-tour" :: Nil

def fromURL(url: String) = scala.io.Source.fromURL(url).getLines().mkString("\n")

val s = System.currentTimeMillis()
time(urls.map(fromURL(_)))
println("time: " + (System.currentTimeMillis - s) + "ms")
```

### Concurrentwordcount
Concurrent Collection support most functions in normal ones.
Here is the word count example from earlier, improved using a parallel collection.
It can use the power of multiple cores without increasing the complexity.

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

def wordcount(str: String): Int = str.split(" ").count("msg" == _)

val num = file.par.map(wordcount).par.reduceLeft(_ + _)

println("wordcount:" + num)
```

### Remote Actor
Actor is not only a concurrency model, it can also be used for distributed computing.
This example builds an EchoServer using an Actor.
Then it creates a client to access the Akka URL.
The usage is the same as with a normal Actor.

```
import akka.actor.{ Actor, ActorSystem, Props }
import com.typesafe.config.ConfigFactory

implicit val system = akka.actor.ActorSystem("RemoteSystem",
  ConfigFactory.load.getConfig("remote"))
class EchoServer extends Actor {
  def receive = {
    case msg: String => println("echo " + msg)
  }
}

val server = system.actorOf(Props[EchoServer], name = "echoServer")

val echoClient = system
  .actorFor("akka://RemoteSystem@127.0.0.1:2552/user/echoServer")
echoClient ! "Hi Remote"

system.shutdown

```

## Practice

### Using Java

Scala can execute Java code very easily. There have already been many examples of this.
Java can also use Scala. This example shows how to use the @BeanProperty Annotation to create a Java Style Bean.
Try to add @BeanProperty before var name. Now the bean contains getter/setter functions.
And the Apache BeanUtils can work correctly.

```
import org.apache.commons.beanutils.BeanUtils
import scala.beans.BeanProperty

class SimpleBean( var name: String) {
}

val bean = new SimpleBean("foo")

println(BeanUtils.describe(bean))
```

### Equality
In Scala == is the same as equals function. It's not the same as Java, but it's more reasonable.
This example defines a equals function, and prints the result.
Correctly writing an equals function is difficult. This example has an issue with subclasses.
Try to change 'class' to 'case class', and delete the equals function.
Case Class correctly generates the equals function for us.

```
class Person(val name: String) {
  override def equals(other: Any) = other match {
    case that: Person => name.equals(that.name)
    case _ => false
  }
}

println(new Person("Black") == new Person("Black"))
```


### Extractor
Extractor objects can deconstruct pattern matches.
This example builds an Email Extractor, only the 'unapply function' is needed.
Scala's Regex contains an extractor, which extracts a List. The List elements sequentially match expressions captured in ().
Extractor is very useful. There are 2 cases in this example.
case user :: domain :: Nil extracts a List. case Email(user, domain) extracts an Email.

```
object Email {
  def apply(user: String, domain: String) = user + "@" + domain

  def unapply(str: String) = new Regex("""(.*)@(.*)""").unapplySeq(str).get match {
    case user :: domain :: Nil => Some(user, domain)
    case _ => None
  }
}

"user@domain.com" match {
  case Email(user, domain) => println(user + "@" + domain)
}
```

### Memory Pattern
Memory Pattern can be used to simplify caching.
In this example, the 'memo function' wraps a function without caching to add the simple cache capability.
In this Fibonacci example, a cache improves performance after the first call.
Try to change fibonacci_(n - 1) + fibonacci_(n - 2) to memo(fibonacci_)(n - 1) + memo(fibonacci_)(n - 2), it can improve more.

```
import scala.collection.mutable.WeakHashMap

val cache = new WeakHashMap[Int, Int]
def memo(f: Int => Int) = (x: Int) => cache.getOrElseUpdate(x, f(x))

def fibonacci_(in: Int): Int = in match {
  case 0 => 0;
  case 1 => 1;
  case n: Int => fibonacci_(n - 1) + fibonacci_(n - 2)
}

val fibonacci: Int => Int = memo(fibonacci_)

val t1 = System.currentTimeMillis()
println(fibonacci(40))
println("it takes " + (System.currentTimeMillis() - t1) + "ms")

val t2 = System.currentTimeMillis()
println(fibonacci(40))
println("it takes " + (System.currentTimeMillis() - t2) + "ms")
```
Implicit Conversion
Implicit can be used to define a Conversion function. Types are automatically implicitly converted when needed.
This example converts String to Date automatically. Implicit is the most important feature when implementing a DSL.

```
  implicit def strToDate(str: String) = new SimpleDateFormat("yyyy-MM-dd").parse(str)

  println("2013-01-01 unix time: " + "2013-01-01".getTime()/1000l)
```

### DSL
DSL is the most powerful tool in Scala. With it Scala code can become more descriptive.
This example generates Json with a DSL. Some of the features look like native features but are created by a DSL.
It's complex to write your own DSL. But it's very easy to use.

```
import org.json4s._
import org.json4s.JsonDSL._

import org.json4s.jackson.JsonMethods._

case class Twitter(id: Long, text: String, publishedAt: Option[java.util.Date])

var twitters = Twitter(1, "hello scala", Some(new Date())) :: Twitter(2, "I like scala tour", None) :: Nil

var json = ("twitters"
  -> twitters.map(
    t => ("id" -> t.id)
      ~ ("text" -> t.text)
      ~ ("published_at" -> t.publishedAt.toString())))

println(pretty(render(json)))
```

### Testing
Scala DSL can make testing even easier.
This example tests a Factorial function. It creates a test case with should/in.
Test cases run concurrently by default.

```
import org.specs2.mutable._

class FactorialSpec extends Specification {
  args.report(color = false)

  def factorial(n: Int) = (1 to n).reduce(_ * _)

  "The 'Hello world' string" should {
    "factorial 3 must be 6" in {
      factorial(3) mustEqual 6
    }
    "factorial 4 must be 6" in {
      factorial(4) must greaterThan(6)
    } 
  }
}
specs2.run(new FactorialSpec)
```


### Simple Build Tool
SBT is a very popular build tool for Scala.
With its help, you can develop Scala even without installing anything except JRE.
This example is to run this Scala Tour in your computer.

```
#Linux/Mac(compile & run):
git clone https://github.com/yankay/scala-tour-zh.git
cd scala-tour-zh
./sbt/sbt stage
./target/start

#Windows(can only compile):
git clone https://github.com/yankay/scala-tour-zh.git
cd scala-tour-zh
sbt\sbt stage
```
