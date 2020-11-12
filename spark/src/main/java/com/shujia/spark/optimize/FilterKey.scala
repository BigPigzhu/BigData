package com.shujia.spark.optimize

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object FilterKey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("app")
    val sc: SparkContext = new SparkContext(conf)
    val lines: RDD[String] = sc.textFile("spark/data/word")

    println("第一个RDD分区数量：" + lines.getNumPartitions)

    val countRDD: RDD[(String, Int)] = lines
      .flatMap(_.split(","))
      .map((_, 1))
      .groupByKey()
      .map(x => (x._1, x._2.toList.sum))

    println("聚合之后RDD分区的数量" + countRDD.getNumPartitions)

    // countRDD.foreach(println)



    /**
      * 采样key  ,g过滤掉导致数据倾斜并且对业务影响不大的key
      *
      */


    val wordRDD: RDD[(String, Int)] = lines
      .flatMap(_.split(","))
      .map((_, 1))

    val top1: Array[(String, Int)] = wordRDD
      .sample(true, 0.1)
      .reduceByKey(_ + _)
      .sortBy(-_._2)
      .take(1)

    //导致数据倾斜额key
    //val key: String = top1(0)._1

    //过滤导致倾斜的key
    wordRDD
      .filter(t => !"null".equals(t._1))
      .groupByKey()
      .map(x => (x._1, x._2.toList.sum))
      .foreach(println)


    while (true) {

    }

  }
}
