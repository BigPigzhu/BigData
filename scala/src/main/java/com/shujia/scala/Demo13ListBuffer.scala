package com.shujia.scala

import scala.collection.mutable.ListBuffer

object Demo13ListBuffer {
  def main(args: Array[String]): Unit = {

    /**
      * 可变集合,  会占用更多的内存空间
      *
      * scala.collection.mutable : 可变集合所在的包
      * scala.collection.immutable ; 不可变集合所在的包
      *
      */

    val ints = new ListBuffer[Int]

    //增加袁术
    ints += 10
    ints += 1
    ints += 2

    //指定下标插入元素
    ints.insert(1, 100)
    println(ints)

    //删除指定下标的袁术
    ints.remove(2)
    println(ints)

    //插入多个元素
    ints ++= List(1, 2, 3)


    //scala中集合类型转换
    ints.toList.toArray.toSet.toIterable.toList


    ints.foreach(println)

  }
}
