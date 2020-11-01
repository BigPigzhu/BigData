package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo3Opt {

  def main(args: Array[String]): Unit = {

    /**
      * 算子
      * 转换算子： 将一个rdd转换成列一个rdd, 懒执行
      *
      * 操作算子： 得到最终结果，  不会翻一个rdd ,  触发任务执行
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8))


    //懒执行
    val rdd2: RDD[Int] = rdd1.map(i => {
      println("rdd2处理逻辑：" + i)
      i * 2
    })


    //触发job执行
    //每一个操作算子都会触发一个job
    rdd2.foreach(println)

    //每一个操作算子都会触发前面所有逻辑执行
    rdd2.foreach(println)


  }

}
