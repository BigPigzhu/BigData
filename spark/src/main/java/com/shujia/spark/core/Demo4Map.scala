package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo4Map {
  def main(args: Array[String]): Unit = {

    /**
      * map:  对rdd中的数据进行处理，传入一行返回一行， 数据总行数不变
      *
      */


    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9))


    //奇数加1，偶数乘2

    val rdd2: RDD[Int] = rdd1.map(i => i % 2 match {
      case 1 => i + 1
      case 0 => i * 2
    })


    rdd2.foreach(println)


  }
}
