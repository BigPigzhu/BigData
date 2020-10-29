package com.shujia.scala

import java.util

object Demo25JavaToScala {
  def main(args: Array[String]): Unit = {

    //创建java中锋集合
    val list = new util.ArrayList[String]()

    list.add("java")
    list.add("scala")
    list.add("spark")

    println(list)

    //将java集合转换成scala集合
    //导入一个java集合转换成scala集合的隐式转换
    import scala.collection.JavaConversions._
    val scalaList: List[String] = list.toList


    //导入将Scala集合转换成java集合的隐式转换
    import scala.collection.JavaConverters._
    val java: util.List[String] = scalaList.asJava

  }
}
