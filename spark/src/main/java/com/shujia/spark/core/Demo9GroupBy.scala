package com.shujia.spark.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Demo9GroupBy {
  def main(args: Array[String]): Unit = {

    /**
      * groupby : 指定一个列来分组
      *
      */


    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)

    val linesRDD: RDD[String] = sc.textFile("spark/data/students.txt")


    val groupbyRDD: RDD[(String, Iterable[String])] = linesRDD.groupBy(line => {
      val clazz: String = line.split(",")(4)

      //指定分组的列
      clazz
    })

    groupbyRDD.foreach(println)

  }
}
