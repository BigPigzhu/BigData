package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo11Union {

  def main(args: Array[String]): Unit = {

    /**
      *
      * union: 将两个rdd合并成一个rdd ，  两个rdd的类型必须一样
      *
      * 合并rdd本质上是在逻辑层面
      * 物理层面没有合并
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)

    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7))
    val rdd2: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7))


    //合并两个rdd,
    val rdd3: RDD[Int] = rdd1.union(rdd2)


    rdd3.foreach(println)

  }

}
