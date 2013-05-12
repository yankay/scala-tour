# Scala 指南

[http://zh.scala-tour.com/](http://zh.scala-tour.com/) 

## 基本

### 表达式和值

在Scala中，几乎所有的语言元素都是表达式。println("hello wolrd")是一个表达式， "hello"+" world"
也是一个表达式。

可以通过val定义一个常量，亦可以通过var定义一个变量。推荐多使用常量。

```
var helloWorld = "hello" + " world" 
println(helloWorld)

val again = " again" 
helloWorld = helloWorld + again
println(helloWorld)
```

### 函数是一等公民

可以使用def来定义一个函数。函数体是一个表达式。

使用Block表达式的时候，默认最后一行的返回是返回值，无需显示指定。

函数还可以像值一样，赋值给var或val。因此他也可以作为参数传给另一个函数。

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



### 借贷模式

由于函数可以像值一样作为参数传递，所以可以方便的实现借贷模式。

这个例子是从/proc/self/stat文件中读取当前进程的pid。

withScanner封装了try-finally块，所以调用者不用再close。

注：当表达式没有返回值时，默认返回Unit。

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


### 按名称传递参数

这个例子演示了按名称传递参数，最末有1/0这个明显会产生异常的计算，运行该程序会产生异常。

试着将def log(msg: String)修改为def log(msg: => String)。由按值传递修改为按名称传递后将不会产生异常。


因为log函数的参数是按名称传递，参数会等到真正访问的时候才会计算，由于logEnable = false，所以被跳过。


按名称传递参数可以减少不必要的计算和异常。


```
val logEnable = false

def log(msg: String) =
	if (logEnable) println(msg)

val MSG = "programing is running"

log(MSG + 1 / 0)
```

### 类定义

可以用class关键字来定义类。并通过new来创建类。
在定义类时可以定义字段，如firstName，lastName。这样做还可以自动生成构造函数。
可以在类中通过def定义函数。var和val定义字段。
函数名是任何字符如+,-,*,/。
例子中obama.age_=(51)的函数调用，可以简化为obama.age = 51 。
obama.age()的函数调用，可以省略小括号，简化为obama.age。

```
class Persion(val firstName: String, val lastName: String) {

	private var _age = 0
	def age = _age
	def age_=(newAge: Int) = _age = newAge

	def fullName() = firstName + " " + lastName

	override def toString() = fullName()
}

val obama: Persion = new Persion("Barack", "Obama")

println("Persion: " + obama)
println("firstName: " + obama.firstName)
println("lastName: " + obama.lastName)
obama.age_=(51)
println("age: " + obama.age())
```


### 鸭子类型

走起来像鸭子，叫起来像鸭子，就是鸭子。
这个例子中使用{ def close(): Unit }作为参数类型。因为任何含有close()的函数的类都可以作为参数。
不必使用继承这种不够灵活的特性。

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


### 柯里化 

这个例子和上面的功能相同。不同的是使用了柯里化（Currying)的技术。
def add(x:Int, y:Int) = x + y 是普通的函数
def add(x:Int) = (y:Int) => x + y 是柯里化后的函数，相当于返回一个匿名函数表达式。
def add(x:Int)(y:Int) = x + y 是上面的简化写法
柯里化可以让我们构造出更像原生语言提供的功能的代码
例子中的withclose(...)(...)换成withclose(...){...}
是否和java中的synchronized关键字用法很像？

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


### 范型

上面的例子可以使用泛型变得更简洁更灵活。
试着将val msg = "123456"修改为val msg = 123456。
虽然msg由String类型变为Int类型，但是由于使用了泛型，代码依旧可以正常运行。

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

Traits就像是有函数体的Interface。使用with关键字来混入。
这个例子是给java.util.ArrayList添加了foreach的功能。
试着再在后面加上with JsonAble，给list添加toJson的能力

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


## 函数式编程

### 模式匹配

模式匹配是类似switch-case特性，但更加灵活；也类似if-else，但更加简约。
这个例子展示的使用用模式匹配实现斐波那契。
使用case来匹配参数，如果case _，则可以匹配任何参数。
这个例子有所不足，当输入负数时，会无限循环。
可以在case后添加if语句判断，将case n: Int 修改为 case n: Int if (n > 1)即可。
可以在case _ 前加上 case n: String => fibonacci(n.toInt)，使之匹配字符串类型。
在最后添加 println(fibonacci(-3))；println(fibonacci("3"))；来检查刚刚修改的效果。


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

case class 顾名思义就是为case语句专门设计的类。
在普通类的基础上添加了和类名一直的工厂方法，
还添加了hashcode,equals和toString方法。
试试最后添加  println(Sum(1,2)) 。
由于使用了require(n >= 0)来检验参数，尝试使用负数，会抛出异常。


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

### 函数式的威力

这个例子是判断一个List中是否含有奇数。
第一行到倒数第二行是使用for循环的过程式编程解决。最后一行是通过函数式编程解决。
通过将函数作为参数，可以使程序极为简洁。其中 _ % 2 == 1 是 (x: Int) => x % 2 == 的简化写法。
相比于Ruby等动态语言,这威力来自于科学而不是魔法

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

### 函数式真正的威力

函数式除了能简化代码外，更重要的是他关注的是Input和Output，函数本身没有副作用。
就是Unix pipeline一样，简单的命令可以组合在一起。
List的filter方法接受一个过滤函数，返回一个新的List
如果你喜欢Unix pipeline的方式，你一定也会喜欢函数式编程。
这个例子是用函数式的代码模拟“cat file | grep 'warn' | grep '2013' | wc”的行为。

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

println("cat file | grep 'warn' | grep '2013' | wc : " 
    + file.filter(_.contains("warn")).filter(_.contains("2013")).size)
```
### Word Count
Word Count是一个MapReduce的一个经典示例。巧合的是，使用函数式的编程法，用类似MapReduce的方法实现word count也是最直观的。
这个例子介绍了List的两个重要的高阶方法map和reduceLeft。
List的map方法接受一个转换函数，返回一个经过转换的List。该例子中会转换为[1,1,1,1]
List的reduceLeft方法接受一个合并函数，依次遍历合并。第一个参数是合并后的值，第二个参数是下一个需要合并的值。
将reduceLeft(_ + _)修改为foldLeft(0)(_ + _)。foldLeft比将reduceLeft更常用，因为他可以提供一个初始参数。
Map和foldLeft可以代替大部分需要for循环的操作，并且使代码更清晰

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

def wordcount(str: String): Int = str.split(" ").count("msg" == _)
  
val num = file.map(wordcount).reduceLeft(_ + _)

println("wordcount:" + num)
```
### 尾递归

这个例子是用尾递归实现foldLeft。尾递归是递归的一种，特点在于会在函数的最末调用自身。
当用List做match case时。可以使用 :: 来解构。返回第一个元素head，和剩余元素tail。
尾递归会在编译期优化，因此不用担心一般递归造成的栈溢出问题。

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

### 更强大的For

循环语句是指令式编程的特产，Scala对其加以改进，成为适应函数式风格的利器。
For循环也是有返回值的，其返回是一个List。在每一轮迭代中加入yield，yield后的值可以加入到List中。
这个例子是使用for循环代替map函数。

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

NullException是Java中最常见的异常，要想避免他只有不断检查null。Scala提供了Option机制来解决。
这个例子包装了可能返回null的getProperty方法，使其返回一个Option。
这样就可以不再漫无目的地null检查。只要Option类型的值即可。
使用pattern match来检查是常见做法。也可以使用getOrElse来提供当为None时的默认值。
给力的是Option还可以看作是最大长度为1的List，其的强大功能都可以使用。
尝试在最后添加  osName.foreach(print _) 。


```

def getProperty(name: String): Option[String] = {
  val value = System.getProperty(name)
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

Lazy可以延迟初始化。加上lazy的字段会在第一次访问的时候初始化。
这个例子是从github获得Scala的版本号，由于访问网络需要较多时间。
如果费尽力气获取到，而调用它的代码却不去访问就会很浪费。
可以使用lazy来延迟获取。

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


## 并发

### 使用Actor

Actor是Scala的并发模型。在2.10之后的版本中，使用[http://akka.io/](Akka)作为其推荐Actor实现。
Actor是类似线程的实体，有一个邮箱。
可以通过system.actorOf来创建,receive获取邮箱消息，！向邮箱发送消息。
这个例子是一个EchoServer，接受信息并打印。

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

### Actor更简化的用法

可以通过更简化的办法声明Actor。
导入akka.actor.ActorDSL中的actor函数。
这个函数可以接受一个Actor的构造器Act，启动并返回Actor。


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
### Actor原理
Actor比线程轻量。在Scala中可以创建数以百万级的Actor。奥秘在于Actor可以复用线程。
Actor和线程是不同的抽象，他们的对应关系是由Dispatcher决定的。
这个例子创建4个Actor，每次调用的时候打印自身线程。
可以发现Actor和线程之间没有一对一的对应关系。
一个Actor可以使用多个线程，一个线程也会被多个Actor复用。

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



### 同步返回

Actor非常适合于较耗时的操作。比如获取网络资源。
这个例子通过调用ask函数来获取一个Future。
在Actor内部通过 sender ! 传递结果。
Future像Option一样有很多高阶方法，可以使用foreach查看结果。


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

### 异步返回 

异步操作可以最大发挥效能。Scala的Futrue很强大，可以异步返回。
可以实现Futrue的onComplete方法。当Futrue结束的时候就会回调。
在调用ask的时候，可以设定超时。

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
### 并发集合

这个例子是访问若干URL，并记录时间。
如果能并发访问，就可以大幅提高性能。
尝试将urls.map修改为urls.par.map。这样每个map中的函数都可以并发执行。
当函数式和并发结合，就会这样让人兴奋。

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

### 并发wordcount
并发集合支持大部分集合的功能。
在前面有一个wordcount例子，也可以用并发集合加以实现。
不增加程序复杂性，却能大幅提高程序利用多核的能力。

```
val file = List("warn 2013 msg", "warn 2012 msg", "error 2013 msg", "warn 2013 msg")

def wordcount(str: String): Int = str.split(" ").count("msg" == _)

val num = file.par.map(wordcount).par.reduceLeft(_ + _)

println("wordcount:" + num)
```

### 远程Actor
Actor是并发模型，也使用于分布式。
这个例子创建一个Echo服务器，通过actorOf来注册自己。
然后在创建一个client，通过akka url来寻址。
除了是通过url创建的，其他使用的方法和普通Actor一样。

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

## 实践

### 使用Java

Scala可以非常方便的互操作，前面已经有大量Scala直接使用Java的例子。
同样Java也可以使用Scala。这个例子演示使用@BeanProperty注解来生成Java Style的Bean。
尝试将在var name前加上@BeanProperty。这样就给bean添加了getter/setter
Apache BeanUtils就可以正常工作。

```
import org.apache.commons.beanutils.BeanUtils
import scala.beans.BeanProperty

class SimpleBean( var name: String) {
}

val bean = new SimpleBean("foo")

println(BeanUtils.describe(bean))
```

### 相等性
在Scala中==操作等效于equals，这一点和Java不同。更自然一些。
这个例子定义了一个equals函数，并验证。
写一个完全正确的equal函数并不容易，这个例子也有子类会不对称的Bug。
尝试将class修改为case class并删除equals函数。
case类会自动生成正确的equals函数。

```
class Person(val name: String) {
  override def equals(other: Any) = other match {
    case that: Person => name.equals(that.name)
    case _ => false
  }
}

println(new Person("Black") == new Person("Black"))
```


### 抽取器
抽取器可以帮助pattern match进行解构。
这个例子是构建一个Email抽取器，只要实现unapply函数就可以了。
Scala的正则表达式会自带抽取器，可以抽取出一个List。List的元素是匹配()里的表达式。
抽取器很有用，短短的例子里就有两处使用抽取器：
case user :: domain :: Nil解构List；case Email(user, domain) 解构Email。

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

### 记忆模式
记忆模式可以解决手动编写存取cache代码的麻烦。
这个例子中，memo可以将一个不含cache函数，包装成一个含有cache功能的。
还是斐波那契的例子，通过cache可以使性能提高。
尝试将fibonacci_(n - 1) + fibonacci_(n - 2)修改memo(fibonacci_)(n - 1) + memo(fibonacci_)(n - 2)，可以提高更多。

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
### 隐式转换
implicit可以定义一个转换函数，可以在下面的使用到的时候自动转换。
这个例子可以将String自动转换为Date类型。隐式转换时实现DSL的重要工具。

```
  implicit def strToDate(str: String) = new SimpleDateFormat("yyyy-MM-dd").parse(str)

  println("2013-01-01 unix time: " + "2013-01-01".getTime()/1000l)
```

### DSL
DSL是Scala最强大武器，Scala可以使一些描述性代码变得极为简单。
这个例子是使用DSL生成JSON。Scala很多看似是语言级的特性也是用DSL做到的。
自己编写DSL有点复杂，但使用方便灵活的。

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

### 测试
Scala DSL可以使测试更方便。
这个例子是测试一个阶乘函数。使用should/in来建立测试用例。
测试是默认并发执行的。

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
SBT是Scala的最佳编译工具，在他的帮助下，
你甚至不需要安装除JRE外的任何东西，来开发Scala。
例如你想在自己的机器上执行这个Scala-Tour

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
