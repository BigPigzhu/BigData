package com.shujia.spark.core

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo22Bro {
  def main(args: Array[String]): Unit = {

    /**
      * 广播变量
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local[8]").setAppName("opt")

    val sc = new SparkContext(conf)

    val rdd1: RDD[String] = sc.textFile("spark/data/students.txt", 10)
    val rdd2: RDD[String] = sc.textFile("spark/data/students.txt", 10)

    //
    //    val list = List("文科一班", "文科二班", "理科一班")
    //
    //
    //    val filterRDD: RDD[String] = rdd1.filter(line => {
    //      val clazz: String = line.split(",")(4)
    //      //使用Driver普通变量
    //      list.contains(clazz)
    //    })
    //
    //
    //    filterRDD.foreach(println)


    val list = List("文科一班", "文科二班", "理科一班")

    //将集合广播

    val broadList: Broadcast[List[String]] = sc.broadcast(list)

    val filterRDD: RDD[String] = rdd1.filter(line => {
      val clazz: String = line.split(",")(4)

      //获取Driver端的广播变量
      val l: List[String] = broadList.value

      l.contains(clazz)
    })

    filterRDD.foreach(println)



    //再所有算子内不能再使用RDD
    // RDD 不能在网络中传输,  因为rdd是逻辑层面的编程模型
    //    rdd1.foreach(i => {
    //
    //      rdd2.foreach(j => {
    //        println(i + j)
    //      })
    //
    //    })

  }
}
