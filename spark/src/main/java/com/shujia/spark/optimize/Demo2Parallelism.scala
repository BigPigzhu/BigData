package com.shujia.spark.optimize

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo2Parallelism {
  def main(args: Array[String]): Unit = {

    /**
      * spark 参数设置优先级
      * 1、代码优先级最高
      * 2、spark-submit 后面指定参数
      * 3、spark 默认配置文件   spark-defaults.conf
      */

    /**
      * spark-submit --class org.apache.spark.examples.SparkPi \
      * --master yarn-client  \
      * --num-executors 8 \
      * --executor-memory 16G \
      * --conf spark.default.parallelism=100 \
      * --conf spark.storage.memoryFraction=0.4 \
      * --conf spark.shuffle.memoryFraction=0.4 \
      * ./lib/spark-examples-1.6.0-hadoop2.6.0.jar 100
      *
      */

    /**
      * coalesce   从分区
      *
      */

    val conf: SparkConf = new SparkConf()
      .setAppName("app")
      .setMaster("local[1]")
      //.set("spark.default.parallelism", "10")// 默认reduce数量
    val sc: SparkContext = new SparkContext(conf)

    //产生小文件
    val rdd: RDD[(String, Int)] = sc.textFile("spark/data/students.txt").map((_, 1))
    println("rdd:" + rdd.getNumPartitions)
    /**
      * shuffle之后的rdd的分区数
      * 1、手动指定优先级最高
      * 2、设置参数 spark.default.parallelism
      * 3、如果前面两个都没设置默认使用前一个rdd的分区数
      */
    val grdd: RDD[(String, Iterable[Int])] = rdd.groupByKey(2)
    println("grdd:" + grdd.getNumPartitions)
    grdd.foreach(println)


  }
}