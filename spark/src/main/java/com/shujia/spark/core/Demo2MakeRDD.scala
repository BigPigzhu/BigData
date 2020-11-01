package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo2MakeRDD {
  def main(args: Array[String]): Unit = {
    // 创建spark环境

    val conf: SparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("spark")

    val sc: SparkContext = new SparkContext(conf)

    /**
      * 构建rdd
      *
      */

      //1、通过读取hdfs文件构建rdd, 如果在本地运行，那么就是本文件
    val rdd1: RDD[String] = sc.textFile("spark/data/words")



    //2、基于本地集合构建rdd

    val list = List(1,2,3,4,5,6,7,8)

    val rdd2: RDD[Int] = sc.parallelize(list)


    rdd2.foreach(println)





  }
}
