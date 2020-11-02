package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

object Demo17PI {
  def main(args: Array[String]): Unit = {

    /**
      *
      * 模拟随机生成点
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local[8]").setAppName("opt")

    val sc = new SparkContext(conf)


    val list = 0 to 100000000

    val rdd1: RDD[Int] = sc.parallelize(list, 8)


    //1 随机生成带你

    val point: RDD[(Double, Double)] = rdd1.map(i => {
      // 随机生成x和y   范围是-1到1

      val x: Double = Random.nextDouble() * 2 - 1
      val y: Double = Random.nextDouble() * 2 - 1

      (x, y)
    })


    //2 统计园内点的数量
    val yuan: RDD[Int] = point.map(kv => {
      val x: Double = kv._1
      val y: Double = kv._2

      //计算到圆心的距离
      val len: Double = x * x + y * y

      if (len <= 1) {
        1
      } else {
        0
      }
    })


    //园内点的数量
    val yuanNum: Double = yuan.sum()


    //总的点的数量
    val size: Int = list.size


    val PI: Double = 4 * (yuanNum / size)

    println(s"PI:$PI")


  }
}
