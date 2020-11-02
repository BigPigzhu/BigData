package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo15MapPartition {
  def main(args: Array[String]): Unit = {
    /**
      * mapPartition; 处理一个分区的数据
      *
      */
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val rdd1: RDD[String] = sc.textFile("spark/data/words")

    //获取分区数
    //getNumPartitions : 不是一个算子，
    println(s"rdd1分区数:${rdd1.getNumPartitions}")


    val rdd2: RDD[String] = rdd1.mapPartitions(iter => {

      println("函数执行")

      val iterator: Iterator[String] = iter.flatMap(line => line.split(","))

      //返回一个集合
      iterator
    })

    rdd2.foreach(println)


    //mapPartitionsWithIndex  ； 带分区编号

    val rdd3: RDD[String] = rdd1.mapPartitionsWithIndex((index, iter) => {
      println(s"分区编号：$index")

      iter
    })

    rdd3.foreach(println)


  }
}
