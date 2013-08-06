# Scala Tour

[http://www.scala-tour.com/](http://www.scala-tour.com/) 

## Basic

### Expressions and Values

In Scala, almost everything is expression.println("hello wolrd")is an expression, "hello"+" world"
is also an expression.

Constant can be created with val, and variable can be created with var.More constants are better.

```
var helloWorld = "hello" + " world" 
println(helloWorld)

val again = " again" 
helloWorld = helloWorld + again
println(helloWorld)
```

### First-class Functions

You can create functions with def.And the function body is an expression.

When the body is a block expression, it returns the value of the last line. So it's no need to use the return keyword

And like values, functions can also be given name by var or val.So it can be passed as an argument to another function.

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



### Loan pattern

For functions can be passed as arguments, the 'Loan' pattern is easy to implement.

This example is to read the self pid from /proc/self/stat.

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

This example shows the call by nameWhen the last line tries to calculate '1 / 0', the program will throw an exception.

Try to change 'def log(msg: String)' to 'def log(msg: => String)'.The program will not throw an exception because it has been changed to call-by-name


Call-by-name means that the argument will be calculated when it be actually called.Because 'logEnable = false', the '1 / 0' would be skiped.


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
This example uses '{ def close(): Unit }' as the type of argument.So any class contains methods 'close()' can be passed.
And there's no need to use 'inherit'?

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

This example is similar with before?The difference between them is this one leverage currying technology?
def add(x:Int, y:Int) = x + y is a normal function
def add(x:Int) = (y:Int) => x + y is a curryed function.The return value is a function expression.
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

Traits look like Java's interfaces, but with function blocks.One class can extend several traits using the with keyword.
This example extends java.util.ArrayList the ability of foreach.
Try to append with JsonAble to extend the ability of toJson

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

Pattern Matching is more flexible than switch-case; And it's simpler than if-else.
This example shows Fibonacci function with pattern matching.
The case keyword is to matching. The case _ means it can match anything.
But the example has a bug. If the input is negative number, it would loop endless.
Try to add if after case. Change case n: Int to case n: Int if (n > 1).
Try to add case n: String => fibonacci(n.toInt) before case _  to matche String type.
Try to add println(fibonacci(-3))?println(fibonacci("3")) to test the program. 


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
It also have hashcode, equality and nice toString methods.
Try to append println(Sum(1,2)) last.
Because if the require(n >= 0), it would throw exception when input is negative.


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

### The prower of functional programing

This example shows whether there is a odd in list.
The code from the 1st line to the last but one is created by imperative programming.And the last line is created by functional programming.
Treating function expression as function arguments can simplify code effectively.The _ % 2 == 1 is the syntactic sugar of (x: Int) => x % 2 == 1.
Ruby's prower if from magic, but Scala's prower is from science.

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

### The true prower of functional programming

Besides simplifying code, the functional programming more take care of Input & Output without side-effect.
Like the Unix pipeline , simple command can be combined together.
The filter method in List can accept a filter function to return a new List.
If you do like the Unix pipeline style, functional programming can be your favorite.
This example is to use scala code to simulate the funcion of "cat file | grep 'warn' | grep '2013' | wc."

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

println("cat file | grep 'warn' | grep '2013' | wc : " 
    + file.filter(_.contains("warn")).filter(_.contains("2013")).size)
```
### Word Count
Word Count is a classics sample for Map Reduce.Map Reduce with functional programming is also a greate way to implement word count.
The example show two important functions 'map' and 'reduceLeft' in List.
The map function accept a translate expression and return the list translated.
The reduceLeft function accept a combine expression and return the combined result.The first argument is the reduced value, and the second argument is the value next.
Try to change reduceLeft(_ + _) into foldLeft(0)(_ + _).foldLeft is more popular than reduceLeft for it can provide a initial value.
Map and foldLeft can replace the for expression, it make code cleaner.

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

def wordcount(str: String): Int = str.split(" ").count("msg" == _)
  
val num = file.map(wordcount).reduceLeft(_ + _)

println("wordcount:" + num)
```
### Tail Recursion

This example show how to implement foldLeft with Tail RecursionTail Recursion is one type of Recursion, it call itself in it's last expression.
??List?match case?????? :: ???????????head??????tail?
Tail Recursion can be optimized in compile time. So it not need to worry about stack overflow.

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

### Prowerful For Expression

Loop expression is feature of imperative programming.So Scala improved it to suit functional programming.
For expression in Scala can also return a List.With 'yield' in loop, value after yield can append to the List.
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

NullException is the most common exception in Java. The only way to avoid it is to check null everywhere.Scala provide a Option feature to solve it.
This example create a getProperty function, and it would return Option instead of null.
So we do not check null everywhere, getting value from Option is enough.
Using pattern match is a common way to get value from Option.    It can also use getOrElse() to set a default value when it's none.
Another important think is that Option has lots of functions in List, So it can work like a list in most of time.
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

Lazy is lazy initial value.The fields with lazy key word can initial when it first access.
This example is to get the Scala Version Code from Github. It takes time because of network latency.
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

Actor is Scala's concurrent model.After the Version of 2.10, [http://akka.io/](Akka) is recommand implement of Actor in Scala.
Actor is a like thread instance with a mailbox.
It can be created by system.actorOf, and using receive to get message, ! to send message.
This example is a EchoServer whitch can receive message then print them.

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

### Let Actor simpler

There is a simpler way to define Actor.
Import the actor function from akka.actor.ActorDSL.
This function can accept a constructer, and return a started Actor.


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
### Actor Implement
Actor is more light weight than thread.Millions of actors can be generated in Scala, the secret is that Actor can reuse thread.
The mapping relationship between Actor and Thread in decided by Dispatcher.
This example create 4 Actors, and it would print it's thread when it invoked.
You can find that there are no fix mapping relationship between Actor and Thread.
A Actor can use multi threads. And a thread can be used by multi Actors.

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

Actor is very suitable for operation need time, for example getting resource from network.
This example is to get a Future from ask function.
In the actor we can use sender ! to return value.
Like Option, Future has lots of functions, the result can be read by foreach.


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

### Asynchronized Return

Asynchronization can provide better performance. Future in Scala is very Prowerful, it can be retured asynchronously.
Future would call the coComplete function when is finished.
It can alse set TIMEOUT when we use ask.

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

This example is to access serval URLs, can recode the time it needs.
If we access them concurrently, the performance can be better.
Try to change the 'urls.map' to 'urls.par.map'.So the functions in map can run concurrently.
It's exciting to combine functional programming and concurrent. 

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

### Concurrent Word Count
Concurrent Collection support most functions in normal ones.
There is word count example before, we can use concurrent collection to improve it.
It can use the prower of multi core without increase the complexty.

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

def wordcount(str: String): Int = str.split(" ").count("msg" == _)

val num = file.par.map(wordcount).par.reduceLeft(_ + _)

println("wordcount:" + num)
```

### Remote Actor
Actor is concurrent model, it can also used for distribute computing.
This example is to build a EchoServer with Actor.
Then create a client with akka url to route.
The usage method is the same with normal actor.

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

Scala can operate Java very easily. There has been lots of samples before.
Java can also use Scala.This example show how to use @BeanProperty Annotation to create Java Style Bean.
Try to add @BeanProperty before var name. So that the bean contains getter/setter.
So the Apache BeanUtils can work correctly.

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
This example define a equals function, and verify it.
Write a correctly equal function is not a easy work. This example also has a issus when it has subclass.
Try to change 'class' to 'case class', and delete the equals function.
Case Class can generate correctly equal function for us.

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
Extractor can help pattern match to extract.
This example is to build a Email Extractor, implement the unapply function is enough.
The regex in Scala contains extractor, it can extract a List.The elements in List is the expression in ().
Extractor is very useful. There are 2 cases in this example.
case user :: domain :: Nil is to extract a List. case Email(user, domain) is to extract an Email.

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

### Memorize Pattern
Memorize Pattern
In this example, memo can wrapper a function without cache ability to be a function with it.
It's the example for Fibonacci, cache can improve it's performance.
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
Implicit Translation
Implicit can be used to define a Translation function. Type can automantical translate with it.
This example can translate String to Data automantical. Implicit is the most important to implement DSL.

```
  implicit def strToDate(str: String) = new SimpleDateFormat("yyyy-MM-dd").parse(str)

  println("2013-01-01 unix time: " + "2013-01-01".getTime()/1000l)
```

### DSL
DSL is most prowerful tool in Scala. With it Scala can let some code more-descriptive.
This example is to generate Json with DSL. Some of the features looks like native features is created by DSL.
It complex to write your own DSL. But it's very to use.

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
Scala DSL can make testing more easier.
This example is to test a Factorial function. It create test case with should/in.
Test case is runned concurrently in default.

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
SBT is more popular management tool for Scala.
With it's help, you can develop Scala even without installing anything except JRE.
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
