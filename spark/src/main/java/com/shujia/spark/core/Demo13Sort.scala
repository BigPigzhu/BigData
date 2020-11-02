package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo13Sort {

  def main(args: Array[String]): Unit = {

    /**
      * sort： 排序
      *
      */


    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val rdd1: RDD[(String, Int)] = sc.parallelize(List(("张三", 23), ("李四", 21), ("王五", 24)))



    //sortBy：  指定一个排序的列

    //ascending  ；默认升序
    val rdd2: RDD[(String, Int)] = rdd1.sortBy(kv => kv._2, ascending = false)

    rdd2.foreach(println)


    // sortBykey  : 通过key排序，默认升序
    val rdd3: RDD[(String, Int)] = rdd1.sortByKey()

    rdd3.foreach(println)




  }

}
