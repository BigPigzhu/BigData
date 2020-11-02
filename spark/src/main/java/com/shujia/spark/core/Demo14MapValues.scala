package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo14MapValues {

  def main(args: Array[String]): Unit = {

    /**
      * mapvalue ； 处理value, key不变
      *
      */


    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val rdd1: RDD[(String, Int)] = sc.parallelize(List(("张三", 23), ("李四", 21), ("王五", 24)))


    val rdd2: RDD[(String, Int)] = rdd1.mapValues(age => age + 1)


    rdd2.foreach(println)

  }

}
