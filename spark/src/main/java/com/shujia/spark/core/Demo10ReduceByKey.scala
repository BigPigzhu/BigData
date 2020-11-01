package com.shujia.spark.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Demo10ReduceByKey {
  def main(args: Array[String]): Unit = {

    /**
      * reduceBykey:  通过key对value进行聚合，需要传入一个聚合函数
      *
      * 只能用于聚合
      *
      * reduceByKey 效率比groupBykey 高   reduceBykey会在map端进行预聚合
      *
      *
      * 能使用reduceBykey的是偶就是用reduceBykey
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)

    val linesRDD: RDD[String] = sc.textFile("spark/data/students.txt")


    val kvRDD: RDD[(String, Int)] = linesRDD.map(line => {
      val clazz: String = line.split(",")(4)

      (clazz, 1)
    })


    val countRDD: RDD[(String, Int)] = kvRDD.reduceByKey((x, y) => x + y)

    countRDD.foreach(println)


  }
}
