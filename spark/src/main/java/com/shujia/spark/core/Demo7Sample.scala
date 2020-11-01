package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo7Sample {

  def main(args: Array[String]): Unit = {

    /**
      *
      * sample : 抽样，传入一个抽样比例
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val rdd1: RDD[String] = sc.textFile("spark/data/students.txt")


    val rdd2: RDD[String] = rdd1.sample(false, 0.5)


    println("rdds:" + rdd2.count())


  }

}
