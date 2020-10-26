package com.shujia.scala

object Demo2Helloworld {

  /**
    * def ： 定义函数的关键字
    * main： 函数名
    * args：参数名
    * Array[String]： 擦拭类型
    * Unit; 相当于void
    *
    * main需要放在object
    *
    * object中所有的方法可以直接电泳
    */

  def main(args: Array[String]): Unit = {
    System.out.println("java")
    println("helloWorld")

    //在scala中使用java类
    //scala自动通过等号右边推测出等号左边类型
    val student = new StudentJava("张三", 23)
    
    //写上类型
    val student1: StudentJava = new StudentJava("张三", 23)

    println(student)

  }


}
