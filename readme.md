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
The tour is can be translate to Chinese by these scripts:
 ```
cat webapp/index.html | scala project/translate-zh.scala  > webapp/index-cn.html
cat scala-tour.md | scala project/translate-zh.scala  > scala-tour-cn.md

 ```
