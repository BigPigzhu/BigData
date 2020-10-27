package com.shujia.scala

object Demo9Fun1 {
  def main(args: Array[String]): Unit = {
    /**
      * scala中的函数，可以独立存在，可以再任何位置定义函数
      *
      */

    // def ; 定义函数的关键字
    // add : 函数名
    // x y  函数的参数
    // Int : 函数返回值类型
    // = 后面是函数的内容


    def add(x: Int, y: Int): Int = {
      val s = x + y
      return s
    }


    //调用函数
    // 函数调用不需要依赖对象
    val s: Int = add(10, 20)
    println(s)

    /**
      * 函数额解简写
      * 1、return 可以省略，将返回值放在最后一行即可
      * 2、如果函数之后一行代码，{} 可以省略
      * 3、返回值类型可以不写，scala会自动推断
      * 4、如果参数列表为空，() 可以省略
      *
      */

    def add1(x: Int, y: Int): Int = x + y

    def print(): Unit = println("scala")

    print()


    /**
      * lambda 表达书,  或者叫匿名函数
      * => ： 前面是函数的参数
      * => 后面是函数的内容和返回值
      *
      */


    val add2 = (x: Int, y: Int) => x + y

    println(add2(10, 20))

  }


}
