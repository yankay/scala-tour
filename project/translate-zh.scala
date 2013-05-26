object translate {
  val translate = scala.collection.mutable.LinkedHashMap[String, String]()
  translate += ("Scala Tour" -> "Scala 指南")
  translate += ("[http://www.scala-tour.com/](http://www.scala-tour.com/)" -> "[http://zh.scala-tour.com/](http://zh.scala-tour.com/)")
  translate += ("## Basic" -> "## 基本")
  translate += ("Expressions and Values" -> "表达式和值")
  translate += ("In Scala, almost everything is expression."-> "在Scala中，几乎所有的语言元素都是表达式。")
  translate += ("is an expression,"-> "是一个表达式，")
  translate += ("is also an expression."-> "也是一个表达式。")
  translate += ("Constant can be created with val, and variable can be created with var."-> "可以通过val定义一个常量，亦可以通过var定义一个变量。")
  translate += ("More constants are better."-> "推荐多使用常量。")
  translate += ("First-class Functions"-> "函数是一等公民")
  translate += ("You can create functions with def."-> "可以使用def来定义一个函数。")
  translate += ("And the function body is an expression."-> "函数体是一个表达式。")
  translate += ("When the body is a block expression, it returns the value of the last line. So it's no need to use the return keyword"-> 
                "使用Block表达式的时候，默认最后一行的返回是返回值，无需显式指定。")
  translate += ("And like values, functions can also be given name by var or val."-> "函数还可以像值一样，赋值给var或val。")
  translate += ("So it can be passed as an argument to another function."-> "因此他也可以作为参数传给另一个函数。")
  translate += ("### Loan pattern" -> "### 借贷模式")
  translate += ("For functions can be passed as arguments, the 'Loan' pattern is easy to implement." -> 
                "由于函数可以像值一样作为参数传递，所以可以方便的实现借贷模式。")
  translate += ("This example is to read the self pid from /proc/self/stat." -> 
                "这个例子是从/proc/self/stat文件中读取当前进程的pid。")
  translate += ("This example is to read the self pid from /proc/self/stat." -> 
                "这个例子是从/proc/self/stat文件中读取当前进程的pid。")  
  translate += ("Because the 'withScanner' function encapsulates the 'try-finally' block," -> 
                "withScanner封装了try-finally块，")
  translate += ("it no need to call 'close()' any more." -> 
                "所以调用者不用再close。")  
  translate += ("PS: the expression's return type is 'Unit' when it no need to return." -> 
                "注：当表达式没有返回值时，默认返回Unit。")  
  translate += ("### Call-by-Name" -> "### 按名称传递参数")
  translate += ("This example shows the call by name" -> 
                "这个例子演示了按名称传递参数，")  
  translate += ("For the last line try to calculate '1 / 0', the program would throw exception." -> 
                "最末有1/0这个明显会产生异常的计算，运行该程序会产生异常。")  
  translate += ("Try to change 'def log(msg: String)' to 'def log(msg: => String)'." -> 
                "试着将def log(msg: String)修改为def log(msg: => String)。")  
  translate += ("The program would not throw expression because it has been change to call-by-name" -> 
                "由按值传递修改为按名称传递后将不会产生异常。")  
  translate += ("Call-by-name means that the argument would be calculate when it be actually called." -> 
                "因为log函数的参数是按名称传递，参数会等到真正访问的时候才会计算，")  
  translate += ("Because 'logEnable = false', the '1 / 0' would be skiped." -> 
                "由于logEnable = false，所以被跳过。")  
  translate += ("Call-by-name can reduce the useless calculate and exception." -> 
                "按名称传递参数可以减少不必要的计算和异常。")  
  translate += ("### Define Class" -> "### 类定义")
  translate += ("The 'class' keyword is to define class, and the 'new' keyword is to create a instance." -> 
                "可以用class关键字来定义类。并通过new来创建类。")  
  translate += ("The fields can be also defined in class, like the 'firstName' and 'lastName'." -> 
                "在定义类时可以定义字段，如firstName，lastName。")  
  translate += ("It would generate constructor with the arguments." -> 
                "这样做还可以自动生成构造函数。")  
  translate += ("Methods can be defined with def, and fields can be defined with val or var" -> 
                "可以在类中通过def定义函数。var和val定义字段。")  
  translate += ("The function name can be any characters like +,-,*,/." -> 
                "函数名是任何字符如+,-,*,/。")  
  translate += ("The 'obama.age_=(51)' can be simplified as 'obama.age = 51'." -> 
                "例子中obama.age_=(51)的函数调用，可以简化为obama.age = 51 。")  
  translate += ("And the 'obama.age()' can be simplified as 'obama.age'." -> 
                "obama.age()的函数调用，可以省略小括号，简化为obama.age。")  
  translate += ("### Duck Typing" -> "### 鸭子类型")
  translate += ("When I see a bird that walks like a duck and swims like a duck and quacks like a duck, I call that bird a duck." -> 
                "走起来像鸭子，叫起来像鸭子，就是鸭子。")
  translate += ("This example use '{ def close(): Unit }' as the type of argument." -> 
                "这个例子中使用{ def close(): Unit }作为参数类型。")  
  translate += ("So any class contains methods 'close()' can be passed." -> 
                "因为任何含有close()的函数的类都可以作为参数。")  
  translate += ("And it no need to use 'inherit'" -> 
                "不必使用继承这种不够灵活的特性。")   
  translate += ("" -> 
                "### 柯里化")   
  translate += ("" -> 
                "这个例子和上面的功能相同。")   
  translate += ("" -> 
                "不同的是使用了柯里化（Currying)的技术。")   
  translate += ("" -> 
                "def add(x:Int, y:Int) = x + y 是普通的函数")   
  translate += ("" -> 
                "def add(x:Int) = (y:Int) => x + y 是柯里化后的函数，")   
  translate += ("" -> 
                "相当于返回一个匿名函数表达式。")   
  translate += ("" -> 
                "def add(x:Int)(y:Int) = x + y 是上面的简化写法")   
  translate += ("" -> 
                "柯里化可以让我们构造出更像原生语言提供的功能的代码")   
  translate += ("" -> 
                "例子中的withclose(...)(...)换成withclose(...){...}")  
  translate += ("" -> 
                "是否和java中的synchronized关键字用法很像？")  
  translate += ("" -> 
                "### 范型")  
  translate += ("" -> 
                "上面的例子可以使用泛型变得更简洁更灵活。")  
  translate += ("" -> 
                """试着将val msg = "123456"修改为val msg = 123456。""")  
  translate += ("" -> 
                "虽然msg由String类型变为Int类型，但是由于使用了泛型，代码依旧可以正常运行。")  
  translate += ("" -> 
                "### Traits")  
  translate += ("" -> 
                "Traits就像是有函数体的Interface。使用with关键字来混入。")  
  translate += ("" -> 
                "这个例子是给java.util.ArrayList添加了foreach的功能。")  
  translate += ("" -> 
                "试着再在后面加上with JsonAble，给list添加toJson的能力")  
  translate += ("" -> 
                "## 函数式编程")  
  translate += ("" -> 
                "### 模式匹配")  
  translate += ("" -> 
                "模式匹配是类似switch-case特性，但更加灵活；也类似if-else，但更加简约。")  
  translate += ("" -> 
                "这个例子展示的使用用模式匹配实现斐波那契。")  
  translate += ("" -> 
                "使用case来匹配参数，如果case _，则可以匹配任何参数。") 
  translate += ("" -> 
                "这个例子有所不足，当输入负数时，会无限循环。") 
  translate += ("" -> 
                "可以在case后添加if语句判断，将case n: Int 修改为 case n: Int if (n > 1)即可。") 
  translate += ("" -> 
                "可以在case _ 前加上 case n: String => fibonacci(n.toInt)，使之匹配字符串类型。") 
  translate += ("" -> 
                "在最后添加 println(fibonacci(-3))；println(fibonacci("3"))；来检查刚刚修改的效果。") 
  translate += ("" -> 
                "### Case Class") 
  translate += ("" -> 
                "case class 顾名思义就是为case语句专门设计的类。") 
  translate += ("" -> 
                "在普通类的基础上添加了和类名一直的工厂方法，") 
  translate += ("" -> 
                "还添加了hashcode,equals和toString方法。") 
  translate += ("" -> 
                "试试最后添加  println(Sum(1,2)) 。") 
  translate += ("" -> 
                "由于使用了require(n >= 0)来检验参数，尝试使用负数，会抛出异常。") 
  translate += ("" -> 
                "### 函数式的威力") 
  translate += ("" -> 
                "这个例子是判断一个List中是否含有奇数。") 
  translate += ("" -> 
                "第一行到倒数第二行是使用for循环的过程式编程解决。") 
  translate += ("" -> 
                "最后一行是通过函数式编程解决。") 
  translate += ("" -> 
                "通过将函数作为参数，可以使程序极为简洁。")
  translate += ("" -> 
                "其中 _ % 2 == 1 是 (x: Int) => x % 2 == 的简化写法。")
  translate += ("" -> 
                "相比于Ruby等动态语言,这威力来自于科学而不是魔法")
  translate += ("" -> 
                "### 函数式真正的威力")
  translate += ("" -> 
                "函数式除了能简化代码外，更重要的是他关注的是Input和Output，函数本身没有副作用。")
  translate += ("" -> 
                "就是Unix pipeline一样，简单的命令可以组合在一起。")
  translate += ("" -> 
                "List的filter方法接受一个过滤函数，返回一个新的List")
  translate += ("" -> 
                "如果你喜欢Unix pipeline的方式，你一定也会喜欢函数式编程。")
  translate += ("" -> 
                "这个例子是用函数式的代码模拟“cat file | grep 'warn' | grep '2013' | wc”的行为。")
  translate += ("" -> 
                "### Word Count")
  translate += ("" -> 
                "Word Count是一个MapReduce的一个经典示例。")
  translate += ("" -> 
                "巧合的是，使用函数式的编程法，用类似MapReduce的方法实现word count也是最直观的。")
  translate += ("" -> 
                "这个例子介绍了List的两个重要的高阶方法map和reduceLeft。")
  translate += ("" -> 
                "List的map方法接受一个转换函数，返回一个经过转换的List。")
  translate += ("" -> 
                "该例子中会转换为[1,1,1,1]")
  translate += ("" -> 
                "List的reduceLeft方法接受一个合并函数，依次遍历合并。")
  translate += ("" -> 
                "第一个参数是合并后的值，第二个参数是下一个需要合并的值。")
  translate += ("" -> 
                "将reduceLeft(_ + _)修改为foldLeft(0)(_ + _)。")
  translate += ("" -> 
                "foldLeft比将reduceLeft更常用，因为他可以提供一个初始参数。")
  translate += ("" -> 
                "Map和foldLeft可以代替大部分需要for循环的操作，并且使代码更清晰")
  translate += ("" -> 
                "### 尾递归")
  translate += ("" -> 
                "这个例子是用尾递归实现foldLeft。")
  translate += ("" -> 
                "尾递归是递归的一种，特点在于会在函数的最末调用自身。")
  translate += ("" -> 
                "当用List做match case时，可以使用 :: 来解构。返回第一个元素head，和剩余元素tail。")
  translate += ("" -> 
                "尾递归会在编译期优化，因此不用担心一般递归造成的栈溢出问题。")
  translate += ("" -> 
                "### 更强大的For")
  translate += ("" -> 
                "循环语句是指令式编程的特产，Scala对其加以改进，成为适应函数式风格的利器。")
  translate += ("" -> 
                "For循环也是有返回值的，其返回是一个List。")
  translate += ("" -> 
                "在每一轮迭代中加入yield，yield后的值可以加入到List中。")
  translate += ("" -> 
                "这个例子是使用for循环代替map函数。")
  translate += ("" -> 
                "### Option")
  translate += ("" -> 
                "NullException是Java中最常见的异常，要想避免他只有不断检查null。")
  translate += ("" -> 
                "Scala提供了Option机制来解决。")
  translate += ("" -> 
                "这个例子包装了可能返回null的getProperty方法，使其返回一个Option。")
  translate += ("" -> 
                "这样就可以不再漫无目的地null检查。只要Option类型的值即可。")
  translate += ("" -> 
                "使用pattern match来检查是常见做法。")
  translate += ("" -> 
                "也可以使用getOrElse来提供当为None时的默认值。")
  translate += ("" -> 
                "给力的是Option还可以看作是最大长度为1的List，其的强大功能都可以使用。")
  translate += ("" -> 
                "尝试在最后添加  osName.foreach(print _) 。")
  translate += ("" -> 
                "### Lazy")
  translate += ("" -> 
                "Lazy可以延迟初始化。")
  translate += ("" -> 
                "加上lazy的字段会在第一次访问的时候初始化。")
  translate += ("" -> 
                "这个例子是从github获得Scala的版本号，由于访问网络需要较多时间。")
  translate += ("" -> 
                "如果费尽力气获取到，而调用它的代码却不去访问就会很浪费。")
  translate += ("" -> 
                "可以使用lazy来延迟获取。")
  translate += ("" -> 
                "## 并发")
  translate += ("" -> 
                "### 使用Actor")
  translate += ("" -> 
                "Actor是Scala的并发模型。")
  translate += ("" -> 
                "在2.10之后的版本中，使用[http://akka.io/](Akka)作为其推荐Actor实现。")
  translate += ("" -> 
                "Actor是类似线程的实体，有一个邮箱。")
  translate += ("" -> 
                "可以通过system.actorOf来创建,receive获取邮箱消息，！向邮箱发送消息。")
  translate += ("" -> 
                "这个例子是一个EchoServer，接受信息并打印。")
  translate += ("" -> 
                "### Actor更简化的用法")
  translate += ("" -> 
                "可以通过更简化的办法声明Actor。")
  translate += ("" -> 
                "导入akka.actor.ActorDSL中的actor函数。")
  translate += ("" -> 
                "这个函数可以接受一个Actor的构造器Act，启动并返回Actor。")
  translate += ("" -> 
                "### Actor原理")
  translate += ("" -> 
                "Actor比线程轻量。")
  translate += ("" -> 
                "在Scala中可以创建数以百万级的Actor。奥秘在于Actor可以复用线程。")
  translate += ("" -> 
                "Actor和线程是不同的抽象，他们的对应关系是由Dispatcher决定的。")
  translate += ("" -> 
                "这个例子创建4个Actor，每次调用的时候打印自身线程。")
  translate += ("" -> 
                "可以发现Actor和线程之间没有一对一的对应关系。")
  translate += ("" -> 
                "一个Actor可以使用多个线程，一个线程也会被多个Actor复用。")
  translate += ("" -> 
                "### 同步返回")
  translate += ("" -> 
                "Actor非常适合于较耗时的操作。比如获取网络资源。")
  translate += ("" -> 
                "这个例子通过调用ask函数来获取一个Future。")
  translate += ("" -> 
                "在Actor内部通过 sender ! 传递结果。")
  translate += ("" -> 
                "Future像Option一样有很多高阶方法，可以使用foreach查看结果。")
  translate += ("" -> 
                "### 异步返回 ")
  translate += ("" -> 
                "异步操作可以最大发挥效能。Scala的Futrue很强大，可以异步返回。")
  translate += ("" -> 
                "可以实现Futrue的onComplete方法。当Futrue结束的时候就会回调。")
  translate += ("" -> 
                "在调用ask的时候，可以设定超时。")
  translate += ("" -> 
                "### 并发集合")
  translate += ("" -> 
                "这个例子是访问若干URL，并记录时间。")
  translate += ("" -> 
                "如果能并发访问，就可以大幅提高性能。")
  translate += ("" -> 
                "尝试将urls.map修改为urls.par.map。这样每个map中的函数都可以并发执行。")
  translate += ("" -> 
                "当函数式和并发结合，就会这样让人兴奋。")
  translate += ("" -> 
                "### 并发wordcount")
  translate += ("" -> 
                "并发集合支持大部分集合的功能。")
  translate += ("" -> 
                "在前面有一个wordcount例子，也可以用并发集合加以实现。")
  translate += ("" -> 
                "不增加程序复杂性，却能大幅提高程序利用多核的能力。")
  translate += ("" -> 
                "### 远程Actor")
  translate += ("" -> 
                "Actor是并发模型，也使用于分布式。这个例子创建一个Echo服务器，通过actorOf来注册自己。")
  translate += ("" -> 
                "然后在创建一个client，通过akka url来寻址。")
  translate += ("" -> 
                "除了是通过url创建的，其他使用的方法和普通Actor一样。")
  translate += ("" -> 
                "## 实践")
  translate += ("" -> 
                "### 使用Java")
  translate += ("" -> 
                "Scala可以非常方便的互操作，前面已经有大量Scala直接使用Java的例子。")
  translate += ("" -> 
                "同样Java也可以使用Scala。")
  translate += ("" -> 
                "这个例子演示使用@BeanProperty注解来生成Java Style的Bean。")
  translate += ("" -> 
                "尝试将在var name前加上@BeanProperty。这样就给bean添加了getter/setter")
  translate += ("" -> 
                "Apache BeanUtils就可以正常工作。")
  translate += ("" -> 
                "### 相等性")
  translate += ("" -> 
                "在Scala中==操作等效于equals，这一点和Java不同。更自然一些。")
  translate += ("" -> 
                "这个例子定义了一个equals函数，并验证。")
  translate += ("" -> 
                "写一个完全正确的equal函数并不容易，这个例子也有子类会不对称的Bug。")
  translate += ("" -> 
                "尝试将class修改为case class并删除equals函数。")
  translate += ("" -> 
                "case类会自动生成正确的equals函数。")
  translate += ("" -> 
                "### 抽取器")
  translate += ("" -> 
                "抽取器可以帮助pattern match进行解构。")
  translate += ("" -> 
                "这个例子是构建一个Email抽取器，只要实现unapply函数就可以了。")
  translate += ("" -> 
                "Scala的正则表达式会自带抽取器，可以抽取出一个List。")
  translate += ("" -> 
                "List的元素是匹配()里的表达式。")
  translate += ("" -> 
                "抽取器很有用，短短的例子里就有两处使用抽取器：")
  translate += ("" -> 
                "case user :: domain :: Nil解构List；case Email(user, domain) 解构Email。")
  translate += ("" -> 
                "### 记忆模式")
  translate += ("" -> 
                "记忆模式可以解决手动编写存取cache代码的麻烦。")
  translate += ("" -> 
                "这个例子中，memo可以将一个不含cache函数，包装成一个含有cache功能的。")
  translate += ("" -> 
                "还是斐波那契的例子，通过cache可以使性能提高。")
  translate += ("" -> 
                "尝试将fibonacci_(n - 1) + fibonacci_(n - 2)修改memo(fibonacci_)(n - 1) + memo(fibonacci_)(n - 2)，可以提高更多。")
  translate += ("" -> 
                "### 隐式转换")
  translate += ("" -> 
                "implicit可以定义一个转换函数，可以在下面的使用到的时候自动转换。")
  translate += ("" -> 
                "这个例子可以将String自动转换为Date类型。隐式转换时实现DSL的重要工具。")
  translate += ("" -> 
                "DSL是Scala最强大武器，Scala可以使一些描述性代码变得极为简单。")
  translate += ("" -> 
                "这个例子是使用DSL生成JSON。Scala很多看似是语言级的特性也是用DSL做到的。")
  translate += ("" -> 
                "自己编写DSL有点复杂，但使用方便灵活的。")
  translate += ("" -> 
                "### 测试")
  translate += ("" -> 
                "Scala DSL可以使测试更方便。")
  translate += ("" -> 
                "这个例子是测试一个阶乘函数。使用should/in来建立测试用例。")
  translate += ("" -> 
                "测试是默认并发执行的。")
  translate += ("" -> 
                "SBT是Scala的最佳编译工具，在他的帮助下，")
  translate += ("" -> 
                "你甚至不需要安装除JRE外的任何东西，来开发Scala。")
  translate += ("" -> 
                "例如你想在自己的机器上执行这个Scala-Tour")


  def main(args: Array[String]) {
    val t = if (args.isEmpty) translate else translate.map(_.swap)
    scala.io.Source.fromInputStream(System.in, "UTF-8").getLines.map(
      t.keys.foldLeft(_)((b, a) => b.replace(a, t.get(a).get))).foreach(println _)
  }
}

