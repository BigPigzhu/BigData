package com.shujia.scala

import scala.collection.mutable
import scala.collection.mutable.HashSet

object Demo15Set {
  def main(args: Array[String]): Unit = {

    /**
      *
      * Set : 无序唯一
      *
      */

    //默认是一个不可变的集合
    val set = Set(100, 1, 2, 3, 1, 2, 2, 1, 1, 12, 1)

    println(set)


    val l = List(1, 2, 3, 1, 1, 21, 2, 3, 1, 2)

    //对list去重
    println(l.distinct)
    println(l.toSet)

    println(l.sum)
    println(l.max)
    println(l.min)
    println(l.distinct)
    println(l.map(i => i + 1))

    l.foreach(println)


    val s1 = Set(1, 2, 3, 4, 5, 6)
    val s2 = Set(4, 5, 6, 7, 8, 9)

    println(s1 | s2)
    println(s1 & s2)
    println(s1.&~(s2))

    /**
      * 可变set
      *
      */


    val hashSet = new mutable.HashSet[String]()


    //增加元素
    hashSet.add("java")

    hashSet += "scala"

    println(hashSet)


    hashSet remove "java"

    hashSet -= "scala"

    println(hashSet)


  }
}
