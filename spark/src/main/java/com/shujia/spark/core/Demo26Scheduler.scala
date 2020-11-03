package com.shujia.spark.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Demo26Scheduler {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setAppName("opt")

    val sc = new SparkContext(conf)


    //读取分数表
    val studentRDD: RDD[String] = sc.textFile("/data/students.txt", 4)


    val kvRDD: RDD[(String, Int)] = studentRDD.map(line => (line.split(",")(4), 1))

    val numRDD: RDD[(String, Int)] = kvRDD.reduceByKey(_ + _)

    val soetRDD: RDD[(String, Int)] = numRDD.sortBy(_._2)

    soetRDD.saveAsTextFile("/data/out10")


    /**
      * spark-submit --master yarn-client --class com.shujia.spark.core.Demo26Scheduler spark-1.0.jar
      *
      */


    while (true){

    }
  }
}
