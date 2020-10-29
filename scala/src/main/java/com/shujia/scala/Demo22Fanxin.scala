package com.shujia.scala


import scala.collection.mutable.ListBuffer

object Demo22Fanxin {
  def main(args: Array[String]): Unit = {

    /**
      * 泛型， 在编译时对变量类型的限制
      * 泛型能够使编写代码更灵活
      * 1、泛型类
      *
      */

    val stu = new Student4[Int]()

    stu.print(12)

    print(3.14)

  }

  //2、泛型方法
  //T 的类型由参数类型决定
  def print[T](s: T): Unit = {

    //如果使用强转 ，容易出错

    // s的类型不确定，可以通过模式匹配处理
    s match {
      case s: String => println("是一个String类型：" + s)
      case i: Int => println("是一个Int类型：" + i)
      case _ => println("其他类型")
    }

  }

}


//  1、泛型类
class Student4[T] {

  def print(t: T): Unit = {
    println(t)
  }

}

