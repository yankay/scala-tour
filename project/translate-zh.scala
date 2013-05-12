object translate {
  val translate = scala.collection.mutable.Map[String, String]()
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
                "使用Block表达式的时候，默认最后一行的返回是返回值，无需显示指定。")
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





  def main(args: Array[String]) {
    val t = if (args.isEmpty) translate else translate.map(_.swap)
    scala.io.Source.fromInputStream(System.in, "UTF-8").getLines.map(
      t.keys.foldLeft(_)((b, a) => b.replace(a, t.get(a).get))).foreach(println _)
  }
}

