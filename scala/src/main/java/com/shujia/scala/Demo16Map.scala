package com.shujia.scala

import scala.collection.mutable
import scala.collection.mutable.HashMap

object Demo16Map {
  def main(args: Array[String]): Unit = {

    /**
      * map  :  key-value
      *
      */

    // 不可变map
    val map = Map("001" -> "张三", "002" -> "李四", ("003", "王五"))

    //通过key获取value
    println(map("001")) //如果key不存在会报错
    println(map.getOrElse("005", "默认值")) //如果key不存在返回默认值

    println(map.keys)
    println(map.values)

    //遍历map

    //elem  是一个二元组
    for (elem <- map) {
      val key: String = elem._1
      val value: String = elem._2
      println(key + "\t" + value)
    }


    //kv 也是一个元组
    map.foreach(kv => {
      val key: String = kv._1
      val value: String = kv._2

      println(key + "\t" + value)
    })

    /**
      * 可变map
      *
      */

    val hashMap = new mutable.HashMap[String, String]()

    //增加元素
    hashMap.put("001", "张三")

    hashMap += "002" -> "李四"


    println(hashMap)

    //获取元素
    println(hashMap.getOrElse("001", "默认值"))
    println(hashMap("002"))

    //删除元素
    hashMap.remove("002")

    println(hashMap)

  }
}
