package com.shujia.spark.core

import org.apache.spark.{Partitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Demo25Partition {
  def main(args: Array[String]): Unit = {

    /**
      * 自定义分区
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local[8]").setAppName("opt")

    val sc = new SparkContext(conf)


    //读取分数表
    val studentRDD: RDD[String] = sc.textFile("spark/data/students.txt", 4)

    /**
      * 文理科保存到不同的文件
      *
      */


    studentRDD
      .map(line => (line.split(",")(4), 1))
      //      .reduceByKey(_ + _, 2)
      .reduceByKey(new MyPartition, _ + _) // 使用自定义分区
      .saveAsTextFile("spark/data/out5")

    /**
      * 默认为什么是hash分区?
      *
      * 因为hash分区能将数据均匀分配到多个reduce中
      *
      */


  }
}

/**
  * 自定义分区
  *
  */
class MyPartition extends Partitioner {
  //分区的数量
  override def numPartitions: Int = 2

  //通过key获取key属于哪一个分区
  override def getPartition(key: Any): Int = {
    key.toString.startsWith("文科") match {
      case true => 0
      case false => 1
    }
  }
}

