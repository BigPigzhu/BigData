package com.shujia.scala

object Demo10Fun2 {

  def main(args: Array[String]): Unit = {

    /**
      * 在面向对象编程中，对象是基本的单位，将对象传来传去（对象是有类型的）
      *
      * 在面向函数编程中，函数是基本的单位，将函数传来传去，（函数是不是也应该有类型？）
      *
      *
      * 函数的类型： 由参数类型和返回值类型觉得
      *
      * def fun(s: String): String = s
      *
      * fun是一个参数未String 返回值未String类型的函数   String => String
      *
      *
      *
      * 函数式编程（高阶函数）
      * 1、以函数作为参数
      * 2、以函数作为返回值
      *
      *
      */

    /**
      *
      * 1、以函数作为参数
      *
      * fun : 的参数是一个函数，（是一个参数未String  返回值未String的函数）
      *
      */


    def fun(f: String => String): Unit = {

      //可以读传进来的函数进行调用
      val str: String = f("java")
      println(str)
    }


    //参数为String  返回值为String的函数
    def fun2(s: String): String = {
      "fun2:" + s
    }

    def fun3(s: String): String = {
      "fun3:" + s
    }


    //调用函数的时候传入一个函数
    //以函数作为参数
    fun(fun2)
    //可以进行多次调用，传入不同的函数
    fun(fun3)


    /**
      * 通过匿名函数简化函数的调用
      *
      * (s: String) => "匿名函数：" + s
      *
      */

    fun((s: String) => "匿名函数：" + s)


  }

}
