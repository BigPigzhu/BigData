package com.shujia.spark.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Demo5Filter {

  def main(args: Array[String]): Unit = {

    /**
      *
      * filter:  过滤数据，
      * 函数返回true 暴力数据，  函数返回false 过滤数据
      *
      * 返回一个新的rdd
      */

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9))


    //取出奇数
    val rdd2: RDD[Int] = rdd1.filter(i => i % 2 == 1)

    //取出偶数
    val rdd3: RDD[Int] = rdd1.filter(i => i % 2 == 0)


    rdd2.foreach(println)

    rdd3.foreach(println)


  }

}
