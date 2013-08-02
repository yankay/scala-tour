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
                "不必使用继承这种不够灵活的特性")   
  translate += ("### Currying" -> 
                "### 柯里化")   
  translate += ("This example is simliar with before" -> 
                "这个例子和上面的功能相同")   
  translate += ("The difference between them is this one leverage currying technology" -> 
                "不同的是使用了柯里化（Currying)的技术")   
  translate += ("is a normal function" -> 
                "是普通的函数")   
  translate += ("is a curryed function." -> 
                "是柯里化后的函数，")   
  translate += ("The return value is a function expression." -> 
                "相当于返回一个匿名函数表达式。")   
  translate += ("is a syntactic sugar" -> 
                "是上面的简化写法")   
  translate += ("Currying can let our codes looks like language's ability." -> 
                "柯里化可以让我们构造出更像原生语言提供的功能的代码")   
  translate += ("Change the withclose(...)(...) to withclose(...){...}" -> 
                "例子中的withclose(...)(...)换成withclose(...){...}")  
  translate += ("Is it simliar with Java's synchronized block?" -> 
                "是否和java中的synchronized关键字用法很像？")  
  translate += ("### Generic" -> 
                "### 范型")  
  translate += ("The sample before can be more simplified with generic." -> 
                "上面的例子可以使用泛型变得更简洁更灵活。")  
  translate += ("""Try to change val msg = "123456" to val msg = 123456.""" -> 
                """试着将val msg = "123456"修改为val msg = 123456。""")  
  translate += ("Although the type of msg changed from String to Int, the program can be also correctly processed." -> 
                "虽然msg由String类型变为Int类型，但是由于使用了泛型，代码依旧可以正常运行。")  
  translate += ("Traits looks like Java's interface with function block." -> 
                "Traits就像是有函数体的Interface。")  
  translate += ("One class can extend several traits using the with keyword." -> 
                "使用with关键字来混入。")    
  translate += ("This example extends java.util.ArrayList the ability of foreach." -> 
                "这个例子是给java.util.ArrayList添加了foreach的功能。")  
  translate += ("Try to append with JsonAble to extend the ability of toJson" -> 
                "试着再在后面加上with JsonAble，给list添加toJson的能力")  
  translate += ("## Functional Programing" -> 
                "## 函数式编程")  
  translate += ("### Pattern Matching" -> 
                "### 模式匹配")  
  translate += ("Pattern Matching is more flexible than switch-case; And it's simpler than if-else." -> 
                "模式匹配是类似switch-case特性，但更加灵活；也类似if-else，但更加简约。")  
  translate += ("This example shows Fibonacci function with pattern matching." -> 
                "这个例子展示的使用用模式匹配实现斐波那契。")  
  translate += ("The case keyword is to matching. The case _ means it can match anything." -> 
                "使用case来匹配参数，如果case _，则可以匹配任何参数。") 
  translate += ("But the example has a bug. If the input is negative number, it would loop endless." -> 
                "这个例子有所不足，当输入负数时，会无限循环。") 
  translate += ("Try to add if after case. Change case n: Int to case n: Int if (n > 1)." -> 
                "可以在case后添加if语句判断，将case n: Int 修改为 case n: Int if (n > 1)即可。") 
  translate += ("Try to add case n: String => fibonacci(n.toInt) before case _  to matche String type." -> 
                "可以在case _ 前加上 case n: String => fibonacci(n.toInt)，使之匹配字符串类型。") 
  translate += ("Try to add println(fibonacci(-3))；println(fibonacci(\"3\")) to test the program. " -> 
                "在最后添加 println(fibonacci(-3))；println(fibonacci(\"3\"))；来检查刚刚修改的效果。") 
  translate += ("Case classes are used to conveniently store and match on the contents of a class." -> 
                "case class 顾名思义就是为case语句专门设计的类。") 
  translate += ("You can construct them without using new." -> 
                "在普通类的基础上添加了和类名一致的工厂方法，") 
  translate += ("It also have hashcode, equality and nice toString methods." -> 
                "还添加了hashcode,equals和toString方法。") 
  translate += ("Try to append println(Sum(1,2)) last." -> 
                "试试最后添加  println(Sum(1,2)) 。") 
  translate += ("Because if the require(n >= 0), it would throw exception when input is negative." -> 
                "由于使用了require(n >= 0)来检验参数，尝试使用负数，会抛出异常。") 
  translate += ("### The prower of functional programing" -> 
                "### 函数式的威力") 
  translate += ("This example shows whether there is a odd in list." -> 
                "这个例子是判断一个List中是否含有奇数。") 
  translate += ("The code from the 1st line to the last but one is created by imperative programming." -> 
                "第一行到倒数第二行是使用for循环的指令式编程解决。") 
  translate += ("And the last line is created by functional programming." -> 
                "最后一行是通过函数式编程解决。") 
  translate += ("Treating function expression as function arguments can simplify code effectively." -> 
                "通过将函数作为参数，可以使程序极为简洁。")
  translate += ("The _ % 2 == 1 is the syntactic sugar of (x: Int) => x % 2 == 1." -> 
                "其中 _ % 2 == 1 是 (x: Int) => x % 2 == 1 的简化写法。")
  translate += ("Ruby's prower if from magic, but Scala's prower is from science." -> 
                "相比于Ruby等动态语言,这威力来自于科学而不是魔法")
  translate += ("### The true prower of functional programming" -> 
                "### 函数式真正的威力")
  translate += ("Besides simplifying code, the functional programming more take care of Input & Output without side-effect." -> 
                "函数式除了能简化代码外，更重要的是他关注的是Input和Output，函数本身没有副作用。")
  translate += ("Like the Unix pipeline , simple command can be combined together." -> 
                "就是Unix pipeline一样，简单的命令可以组合在一起。")
  translate += ("The filter method in List can accept a filter function to return a new List." -> 
                "List的filter方法接受一个过滤函数，返回一个新的List。")
  translate += ("If you do like the Unix pipeline style, functional programming can be your favorite." -> 
                "如果你喜欢Unix pipeline的方式，你一定也会喜欢函数式编程。")
  translate += ("This example is to use scala code to simulate the funcion of \"cat file | grep 'warn' | grep '2013' | wc.\"" -> 
                "这个例子是用函数式的代码模拟“cat file | grep 'warn' | grep '2013' | wc”的行为。")
  translate += ("### Word Count" -> 
                "### Word Count")
  translate += ("Word Count is a classics sample for Map Reduce." -> 
                "Word Count是一个MapReduce的一个经典示例。")
  translate += ("Map Reduce with functional programming is also a greate way to implement word count." -> 
                "巧合的是，使用函数式的编程法，用类似MapReduce的方法实现word count也是最直观的。")
  translate += ("The example show two important functions 'map' and 'reduceLeft' in List." -> 
                "这个例子介绍了List的两个重要的高阶方法map和reduceLeft。")
  translate += ("The map function accept a translate expression and return the list translated." -> 
                "List的map方法接受一个转换函数，返回一个经过转换的List。")
  translate += ("The reduceLeft function accept a combine expression and return the combined result." -> 
                "List的reduceLeft方法接受一个合并函数，依次遍历合并。")
  translate += ("The first argument is the reduced value, and the second argument is the value next." -> 
                "第一个参数是合并后的值，第二个参数是下一个需要合并的值。")
  translate += ("Try to change reduceLeft(_ + _) into foldLeft(0)(_ + _)." -> 
                "将reduceLeft(_ + _)修改为foldLeft(0)(_ + _)。")
  translate += ("foldLeft is more popular than reduceLeft for it can provide a initial value." -> 
                "foldLeft比将reduceLeft更常用，因为他可以提供一个初始参数。")
  translate += ("Map and foldLeft can replace the for expression, it make code cleaner." -> 
                "Map和foldLeft可以代替大部分需要for循环的操作，并且使代码更清晰")
  translate += ("### Tail Recursion" -> 
                "### 尾递归")
  translate += ("This example show how to implement foldLeft with Tail Recursion" -> 
                "这个例子是用尾递归实现foldLeft。")
  translate += ("Tail Recursion is one type of Recursion, it call itself in it's last expression." -> 
                "尾递归是递归的一种，特点在于会在函数的最末调用自身。")
  translate += ("List can be pattern match by '::', the first elements returned is head, the others are tails." -> 
                "当用List做match case时，可以使用 :: 来解构。返回第一个元素head，和剩余元素tail。")
  translate += ("Tail Recursion can be optimized in compile time. So it not need to worry about stack overflow." -> 
                "尾递归会在编译期优化，因此不用担心一般递归造成的栈溢出问题。")
  translate += ("### Prowerful For Expression" -> 
                "### 更强大的For")
  translate += ("Loop expression is feature of imperative programming.So Scala improved it to suit functional programming." -> 
                "循环语句是指令式编程的特产，Scala对其加以改进，成为适应函数式风格的利器。")
  translate += ("For expression in Scala can also return a List." -> 
                "For循环也是有返回值的，其返回是一个List。")
  translate += ("With 'yield' in loop, value after yield can append to the List." -> 
                "在每一轮迭代中加入yield，yield后的值可以加入到List中。")
  translate += ("This example replaces the map function with for loop." -> 
                "这个例子是使用for循环代替map函数。")
  translate += ("### Option" -> 
                "### Option")
  translate += ("NullException is the most common exception in Java. The only way to avoid it is to check null everywhere." -> 
                "NullException是Java中最常见的异常，要想避免他只有不断检查null。")
  translate += ("Scala provide a Option feature to solve it." -> 
                "Scala提供了Option机制来解决。")
  translate += ("This example create a getProperty function, and it would return Option instead of null." -> 
                "这个例子包装了可能返回null的getProperty方法，使其返回一个Option。")
  translate += ("So we do not check null everywhere, getting value from Option is enough." -> 
                "这样就可以不再漫无目的地null检查。只要Option类型的值即可。")
  translate += ("Using pattern match is a common way to get value from Option.    " -> 
                "使用pattern match来检查是常见做法。")
  translate += ("It can also use getOrElse() to set a default value when it's none." -> 
                "也可以使用getOrElse来提供当为None时的默认值。")
  translate += ("Another important think is that Option has lots of functions in List, So it can work like a list in most of time." -> 
                "给力的是Option还可以看作是最大长度为1的List，其的强大功能都可以使用。")
  translate += ("Try to add 'osName.foreach(print _)' at last." -> 
                "尝试在最后添加  osName.foreach(print _) 。")
  translate += ("### Lazy" -> 
                "### Lazy")
  translate += ("Lazy is lazy initial value." -> 
                "Lazy可以延迟初始化。")
  translate += ("The fields with lazy key word can initial when it first access." -> 
                "加上lazy的字段会在第一次访问的时候初始化。")
  translate += ("This example is to get the Scala Version Code from Github. It takes time because of network latency." -> 
                "这个例子是从github获得Scala的版本号，由于访问网络需要较多时间。")
  translate += ("It waste of time that if we get it with latency but we do not use it later." -> 
                "如果费尽力气获取到，而调用它的代码却不去访问就会很浪费。")
  translate += ("So we can get it with lazy." -> 
                "可以使用lazy来延迟获取。")
  translate += ("### Using Actor" -> 
                "### 使用Actor")
  translate += ("Actor is Scala's concurrent model." -> 
                "Actor是Scala的并发模型。")
  translate += ("After the Version of 2.10, [http://akka.io/](Akka) is recommand implement of Actor in Scala." -> 
                "在2.10之后的版本中，使用[http://akka.io/](Akka)作为其推荐Actor实现。")
  translate += ("Actor is a like thread instance with a mailbox." -> 
                "Actor是类似线程的实体，有一个邮箱。")
  translate += ("It can be created by system.actorOf, and using receive to get message, ! to send message." -> 
                "可以通过system.actorOf来创建,receive获取邮箱消息，！向邮箱发送消息。")
  translate += ("This example is a EchoServer whitch can receive message then print them." -> 
                "这个例子是一个EchoServer，接受信息并打印。")
  translate += ("### Let Actor simpler" -> 
                "### Actor更简化的用法")
  translate += ("There is a simpler way to define Actor." -> 
                "可以通过更简化的办法声明Actor。")
  translate += ("Import the actor function from akka.actor.ActorDSL." -> 
                "导入akka.actor.ActorDSL中的actor函数。")
  translate += ("This function can accept a constructer, and return a started Actor." -> 
                "这个函数可以接受一个Actor的构造器Act，启动并返回Actor。")
  translate += ("### Actor Implement" -> 
                "### Actor原理")
  translate += ("Actor is more light weight than thread." -> 
                "Actor比线程轻量。")
  translate += ("Millions of actors can be generated in Scala, the secret is that Actor can reuse thread." -> 
                "在Scala中可以创建数以百万级的Actor。奥秘在于Actor可以复用线程。")
  translate += ("The mapping relationship between Actor and Thread in decided by Dispatcher." -> 
                "Actor和线程是不同的抽象，他们的对应关系是由Dispatcher决定的。")
  translate += ("This example create 4 Actors, and it would print it's thread when it invoked." -> 
                "这个例子创建4个Actor，每次调用的时候打印自身线程。")
  translate += ("You can find that there are no fix mapping relationship between Actor and Thread." -> 
                "可以发现Actor和线程之间没有一对一的对应关系。")
  translate += ("A Actor can use multi threads. And a thread can be used by multi Actors." -> 
                "一个Actor可以使用多个线程，一个线程也会被多个Actor复用。")
  translate += ("### Synchronized Return" -> 
                "### 同步返回")
  translate += ("Actor is very suitable for operation need time, for example getting resource from network." -> 
                "Actor非常适合于较耗时的操作。比如获取网络资源。")
  translate += ("This example is to get a Future from ask function." -> 
                "这个例子通过调用ask函数来获取一个Future。")
  translate += ("In the actor we can use sender ! to return value." -> 
                "在Actor内部通过 sender ! 传递结果。")
  translate += ("Like Option, Future has lots of functions, the result can be read by foreach." -> 
                "Future像Option一样有很多高阶方法，可以使用foreach查看结果。")
  translate += ("### Asynchronized Return" -> 
                "### 异步返回 ")
  translate += ("Asynchronization can provide better performance. Future in Scala is very Prowerful, it can be retured asynchronously." -> 
                "异步操作可以最大发挥效能。Scala的Futrue很强大，可以异步返回。")
  translate += ("Future would call the coComplete function when is finished." -> 
                "可以实现Futrue的onComplete方法。当Futrue结束的时候就会回调。")
  translate += ("It can alse set TIMEOUT when we use ask." -> 
                "在调用ask的时候，可以设定超时。")
  translate += ("### Concurrent Collection" -> 
                "### 并发集合")
  translate += ("This example is to access serval URLs, can recode the time it needs." -> 
                "这个例子是访问若干URL，并记录时间。")
  translate += ("If we access them concurrently, the performance can be better." -> 
                "如果能并发访问，就可以大幅提高性能。")
  translate += ("Try to change the 'urls.map' to 'urls.par.map'.So the functions in map can run concurrently." -> 
                "尝试将urls.map修改为urls.par.map。这样每个map中的函数都可以并发执行。")
  translate += ("It's exciting to combine functional programming and concurrent. " -> 
                "当函数式和并发结合，就会这样让人兴奋。")
  translate += ("### Concurrent Word Count" -> 
                "### 并发wordcount")
  translate += ("Concurrent Collection support most functions in normal ones." -> 
                "并发集合支持大部分集合的功能。")
  translate += ("There is word count example before, we can use concurrent collection to improve it." -> 
                "在前面有一个wordcount例子，也可以用并发集合加以实现。")
  translate += ("It can use the prower of multi core without increase the complexty." -> 
                "不增加程序复杂性，却能大幅提高程序利用多核的能力。")
  translate += ("### Remote Actor" -> 
                "### 远程Actor")
  translate += ("Actor is concurrent model, it can also used for distribute computing." -> 
                "Actor是并发模型，也使用于分布式。")
  translate += ("This example is to build a EchoServer with Actor." -> 
                "这个例子创建一个Echo服务器，通过actorOf来注册自己。")
  translate += ("Then create a client with akka url to route." -> 
                "然后再创建一个client，通过akka url来寻址。")
  translate += ("The usage method is the same with normal actor." -> 
                "除了是通过url创建的，其他使用的方法和普通Actor一样。")
  translate += ("## Practice" -> 
                "## 实践")
  translate += ("### Using Java" -> 
                "### 使用Java")
  translate += ("Scala can operate Java very easily. There has been lots of samples before." -> 
                "Scala和Java可以非常方便的互操作，前面已经有大量Scala直接使用Java的例子。")
  translate += ("Java can also use Scala." -> 
                "同样Java也可以使用Scala。")
  translate += ("This example show how to use @BeanProperty Annotation to create Java Style Bean." -> 
                "这个例子演示使用@BeanProperty注解来生成Java Style的Bean。")
  translate += ("Try to add @BeanProperty before var name. So that the bean contains getter/setter." -> 
                "尝试将在var name前加上@BeanProperty。这样就给bean添加了getter/setter")
  translate += ("So the Apache BeanUtils can work correctly." -> 
                "Apache BeanUtils就可以正常工作。")
  translate += ("### Equality" -> 
                "### 相等性")
  translate += ("In Scala == is the same as equals function. It's not the same as Java, but it's more reasonable." -> 
                "在Scala中==操作等效于equals，这一点和Java不同。更自然一些。")
  translate += ("This example define a equals function, and verify it." -> 
                "这个例子定义了一个equals函数，并验证。")
  translate += ("Write a correctly equal function is not a easy work. This example also has a issus when it has subclass." -> 
                "写一个完全正确的equal函数并不容易，这个例子也有子类会不对称的Bug。")
  translate += ("Try to change 'class' to 'case class', and delete the equals function." -> 
                "尝试将class修改为case class并删除equals函数。")
  translate += ("Case Class can generate correctly equal function for us." -> 
                "case类会自动生成正确的equals函数。")
  translate += ("### Extractor" -> 
                "### 抽取器")
  translate += ("Extractor can help pattern match to extract." -> 
                "抽取器可以帮助pattern match进行解构。")
  translate += ("This example is to build a Email Extractor, implement the unapply function is enough." -> 
                "这个例子是构建一个Email抽取器，只要实现unapply函数就可以了。")
  translate += ("The regex in Scala contains extractor, it can extract a List." -> 
                "Scala的正则表达式会自带抽取器，可以抽取出一个List。")
  translate += ("The elements in List is the expression in ()." -> 
                "List的元素是匹配()里的表达式。")
  translate += ("Extractor is very useful. There are 2 cases in this example." -> 
                "抽取器很有用，短短的例子里就有两处使用抽取器：")
  translate += ("case user :: domain :: Nil is to extract a List. case Email(user, domain) is to extract an Email." -> 
                "case user :: domain :: Nil解构List；case Email(user, domain) 解构Email。")
  translate += ("### Memorize Pattern" -> 
                "### 记忆模式")
  translate += ("Memorize Pattern" -> 
                "记忆模式可以解决手动编写存取cache代码的麻烦。")
  translate += ("In this example, memo can wrapper a function without cache ability to be a function with it." -> 
                "这个例子中，memo可以将一个不含cache函数，包装成一个含有cache功能的。")
  translate += ("It's the example for Fibonacci, cache can improve it's performance." -> 
                "还是斐波那契的例子，通过cache可以使性能提高。")
  translate += ("Try to change fibonacci_(n - 1) + fibonacci_(n - 2) to memo(fibonacci_)(n - 1) + memo(fibonacci_)(n - 2), it can improve more." -> 
                "尝试将fibonacci_(n - 1) + fibonacci_(n - 2)修改memo(fibonacci_)(n - 1) + memo(fibonacci_)(n - 2)，可以提高更多。")
  translate += ("Implicit Translation" -> 
                "### 隐式转换")
  translate += ("Implicit can be used to define a Translation function. Type can automantical translate with it." -> 
                "implicit可以定义一个转换函数，可以在下面的使用到的时候自动转换。")
  translate += ("This example can translate String to Data automantical. Implicit is the most important to implement DSL." -> 
                "这个例子可以将String自动转换为Date类型。隐式转换时实现DSL的重要工具。")
  translate += ("DSL is most prowerful tool in Scala. With it Scala can let some code more-descriptive." -> 
                "DSL是Scala最强大武器，Scala可以使一些描述性代码变得极为简单。")
  translate += ("This example is to generate Json with DSL. Some of the features looks like native features is created by DSL." -> 
                "这个例子是使用DSL生成JSON。Scala很多看似是语言级的特性也是用DSL做到的。")
  translate += ("It complex to write your own DSL. But it's very to use." -> 
                "自己编写DSL有点复杂，但使用方便灵活的。")
  translate += ("### Testing" -> 
                "### 测试")
  translate += ("Scala DSL can make testing more easier." -> 
                "Scala DSL可以使测试更方便。")
  translate += ("This example is to test a Factorial function. It create test case with should/in." -> 
                "这个例子是测试一个阶乘函数。使用should/in来建立测试用例。")
  translate += ("Test case is runned concurrently in default." -> 
                "测试是默认并发执行的。")
  translate += ("SBT is more popular management tool for Scala." -> 
                "SBT是Scala的最佳编译工具。")
  translate += ("With it's help, you can develop Scala even without installing anything except JRE." -> 
                "在他的帮助下，你甚至不需要安装除JRE外的任何东西，来开发Scala。")
  translate += ("This example is to run this Scala Tour in your computer." -> 
                "例如你想在自己的机器上执行这个Scala-Tour。")
  translate += ("## Concurrent" -> 
                "## 并发")

  def main(args: Array[String]) {
    val t = if (args.isEmpty) translate else translate.map(_.swap)
    scala.io.Source.fromInputStream(System.in, "UTF-8").getLines.map(
      t.keys.foldLeft(_)((b, a) => b.replace(a, t.get(a).get))).foreach(println _)
  }
}
