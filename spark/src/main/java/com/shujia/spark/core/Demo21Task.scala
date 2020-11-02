package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo21Task {
  def main(args: Array[String]): Unit = {

    /**
      *
      * 通过宽窄依赖切分stage , 每一个stage是一组可以并行计算的task
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local[8]").setAppName("opt")

    val sc = new SparkContext(conf)
    val rdd1: RDD[String] = sc.textFile("spark/data/students.txt")


    val rdd2: RDD[String] = rdd1.map(line => line.split(",")(3))


    val rdd3: RDD[String] = rdd2.filter(gender => "男".equals(gender))


    val rdd4: RDD[(String, Int)] = rdd3.map((_, 1))


    val rdd5: RDD[(String, Int)] = rdd4.reduceByKey(_ + _, 4)


    rdd5.foreach(println)


    while (true) {

    }


  }
}
