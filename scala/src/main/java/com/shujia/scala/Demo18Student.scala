package com.shujia.scala

import scala.io.Source

object Demo18Student {
  def main(args: Array[String]): Unit = {

    //1、读取数据
    val lines: List[String] = Source.fromFile("scala/data/students.txt").getLines().toList

    /**
      *
      * 1、统计班级人数
      *
      */


    //1、取出班级
    val clazzs: List[String] = lines.map((line: String) => line.split(",")(4))


    //2、按照班级分组

    val map: Map[String, List[String]] = clazzs.groupBy(clazz => clazz)


    //3、统计班级人数
    val clazzNum: Map[String, Int] = map.map(kv => {
      val clazz: String = kv._1
      val values: List[String] = kv._2
      //班级人数
      val num: Int = values.size

      (clazz, num)
    })

    clazzNum.foreach(println)

    /**
      * 取出性别为男学生
      *
      */

    val nan: List[String] = lines.filter(line => {
      val gender: String = line.split(",")(3)

      /**
        * filter  : 对数据进行过滤，  不会啊改变源数据格式
        * 1、如果函数返回true保留数据、
        * 2、如果函数返回flase， 过滤数据
        *
        */
      "男".equals(gender)
    })

    nan.foreach(println)


  }
}
