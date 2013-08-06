# Scala Tour

A cooler tour the same as scala language 

[http://www.scala-tour.com/](http://www.scala-tour.com/) 


## Compile And Run
 ```
Linux/Mac:
./sbt/sbt stage
./target/start

Windows can only compile
sbt\sbt stage
 ```
view 
http://localhost:8080/#/welcome

## Pure Text
[Scala tour](https://github.com/yankay/scala-tour/blob/master/scala-tour.md)

## Translate
The tour is written by Chinese firstly. And it can be translate to English by these scripts:
 ```
cat webapp/index-cn.html | scala project/translate-zh.scala re > webapp/index.html
cat scala-tour-cn.md | scala project/translate-zh.scala re > scala-tour.md
 ```
