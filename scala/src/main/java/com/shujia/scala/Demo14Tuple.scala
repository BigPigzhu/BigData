package com.shujia.scala

object Demo14Tuple {
  def main(args: Array[String]): Unit = {

    /**
      * 元组： 不可变集合
      *
      */

    //可以通过 小括号定义
    val t = (1, 2, 3, 4, 5, 6)

    val t2 = Tuple4(1, 2, 3, 5)

    println(t2._3)

    //元组可以直接通过  _下标的方式获取数据
    //在写代码的时候就已经明确了集合的长度，不可能下标越界

    println(t._1)
    println(t._3)
    println(t._2)


    // 可以通过-> 快捷生成一个二元组
    println("001" -> "java")


  }
}
