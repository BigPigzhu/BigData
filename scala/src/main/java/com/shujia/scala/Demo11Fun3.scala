package com.shujia.scala

import scala.collection.mutable.ListBuffer

object Demo11Fun3 {
  def main(args: Array[String]): Unit = {

    /**
      * 函数的应用
      *
      */


    val list: List[Int] = List(1, 2, 3, 4, 5, 6, 7)

    val rdd = new RDD(list)

    rdd.print()

    rdd.add(1)

    rdd.print()

    rdd.inc(2)

    rdd.print()

    rdd.map((value: Int) => value - 100)
    rdd.map((value: Int) => value + 100)
    rdd.map((value: Int) => value / 100)
    rdd.map((value: Int) => value * 100)


    /**
      * 面向接口编程
      * 将不一样额逻辑抽象成功接口（java）或者函数(scala),让用户自定义逻辑
      *
      *
      */


    val list2: List[Int] = List(1, 2, 3, 4, 5, 6, 7)

    val rdd2 = new RDD(list2)

    /**
      * 对集合中的元素做什么操作完全由用户自定义
      *
      */

    rdd2.map((value: Int) => value + 1)

    rdd2.map((value: Int) => value / 2)


    //简写，类型自动推断
    rdd2.map(value => value + 2)

    rdd2.print()

    /**
      * 子啊集合中已经自带了map函数
      *
      */

    val ints = List(1, 2, 3, 4, 5, 6, 7, 8)

    val ints2: List[Int] = ints.map(i => i + 1)

    println(ints2)


  }
}

class RDD(list: List[Int]) {

  // List 是一个不可变的集合
  var _list: List[Int] = list


  //对集合进行相加
  def add(k: Int) = {

    //ListBuffer  可变集合
    val listBuffer = new ListBuffer[Int]

    var i = 0
    while (i < _list.size) {

      val value: Int = _list(i)

      //scala中. 和 （）  都可以省略
      //listBuffer.+=(value + k)

      //插入元素
      listBuffer += value + k

      i = i + 1
    }

    _list = listBuffer.toList

  }

  //对集合进行相减
  def inc(k: Int) = {

    //ListBuffer  可变集合
    val listBuffer = new ListBuffer[Int]

    var i = 0
    while (i < _list.size) {

      val value: Int = _list(i)

      //scala中. 和 （）  都可以省略
      //listBuffer.+=(value + k)

      //插入元素
      listBuffer += value - k

      i = i + 1
    }

    _list = listBuffer.toList

  }


  // 可以对集合中的元素做任何操作

  def map(f: Int => Int): Unit = {

    //ListBuffer  可变集合
    val listBuffer = new ListBuffer[Int]

    var i = 0
    while (i < _list.size) {

      val value: Int = _list(i)

      //scala中. 和 （）  都可以省略
      //listBuffer.+=(value + k)

      //将具体的操作抽象成一个函数，让用户取自定义
      val j = f(value)

      //插入元素
      listBuffer += j

      i = i + 1
    }

    _list = listBuffer.toList

  }


  def print(): Unit = {
    println(_list)
  }
}
