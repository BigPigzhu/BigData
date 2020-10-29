package com.shujia.scala

object Demo23Implicit {
  def main(args: Array[String]): Unit = {

    val str: String = "100"

    val int: Int = str.toInt


    /**
      * 隐式转换
      * 动态给类增加新的方法
      *
      * 将对象的类型转换成其他类型
      */


    //1、隐式转换方法

    /**
      * 隐式转换
      * 1、和方法名无关
      * 2、和方法的参数类型返回值类型有关，同一个作用域中不能有两个参数类型和返回值类型一样的隐式转换
      *
      */

    implicit def intToString(i: Int): String = {
      println("隐式转换方法被调用")
      i.toString
    }

    implicit def doubleToString(i: Double): String = {
      println("隐式转换方法被调用")
      i.toString
    }


    def print(s: String): Unit = {
      println(s)
    }

    print("java")

    //显示转换
    //print(1.toString)

    //方法在调用的时候会在当前作用域中找一个类型可以匹配上的隐式转换
    print(100)
    print(3.14)

    //结果相当于显示的调用类型转换方法
    print(intToString(100))


    //在东区作用域中有一个int ---> String的方法，所以所有的int类型都有String类型的方法
    val i: Int = 100

    println(i.startsWith("1"))


  }
}
