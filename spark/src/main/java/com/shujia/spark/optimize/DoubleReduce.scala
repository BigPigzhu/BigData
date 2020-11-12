package com.shujia.spark.optimize

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

object DoubleReduce {

  /**
    * 双重聚合
    * 一般适用于  业务不复杂的情况
    *
    */
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("app")
    val sc: SparkContext = new SparkContext(conf)
    val lines: RDD[String] = sc.textFile("spark/data/word")

    val wordRDD: RDD[String] = lines
      .flatMap(_.split(","))
      .filter(!_.equals(""))

    // 对每一个key打上随机5以内前缀
    wordRDD.map(word => {
      val pix: Int = Random.nextInt(5)
      (pix + "-" + word, 1)
    })
      .groupByKey() //第一次聚合

      .map(t => (t._1, t._2.toList.sum))
      .map(t => {
        ///去掉随机前缀
        (t._1.split("-")(1), t._2)
      })

      .groupByKey() //第二次聚合

      .map(t => (t._1, t._2.toList.sum))
      .foreach(println)

    while (true) {

    }

  }
}
