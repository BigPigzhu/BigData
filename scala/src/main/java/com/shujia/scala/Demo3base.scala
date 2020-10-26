package com.shujia.scala

object Demo3base {
  def main(args: Array[String]): Unit = {

    /**
      * 变量
      * 类型推断，scala会自动推断变量类型
      *
      *
      * val ；常量，不可变 （不能再指向其他的对象） 相当于java中 final
      * var ：变量
      *
      */

    val i: Int = 10

    var j = 100

    //修改变量的值
    j = 20
    println(j)


    //多态，父类引用指向子类对象
    val a: Any = 100


    val l: Long = 1000L


    //scala中的字符串用的就是java中的字符串
    val s: String = "10"


    //javaa的方式进行类型转换
    println(Integer.parseInt(s))


    //scala进行类型转换
    // String类中本来没有toInt方法，scala可以动态增加方法（隐式转换）
    println(s.toInt)
    println(s.toDouble)
    println(s.toLong)


    val b = 100
    println(b.toDouble)

    /**
      * scala基础类型之间都可以通过to 的方式进行类型转换
      *
      */


    /**
      * scala中字符串的拼接
      *
      */

    val s1 = "java"
    val s2 = "scala"


    //内部使用的是java的Stringbuilder
    val s3 = s"$s1\t$s2"
    println(s3)


    //java中国字符串拼接
    val builder = new StringBuilder

    builder.append(s1)
    builder.append("\t")
    builder.append(s2)

    println(builder.toString())


  }
}
