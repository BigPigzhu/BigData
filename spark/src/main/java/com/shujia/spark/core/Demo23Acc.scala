package com.shujia.spark.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator

object Demo23Acc {
  def main(args: Array[String]): Unit = {

    /**
      * 累加器
      *
      *
      * 如果在算子内部对Driver端的一个普通变量进行累加,Drier的变量不会生效
      *
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local[8]").setAppName("opt")

    val sc = new SparkContext(conf)

    val rdd1: RDD[String] = sc.textFile("spark/data/students.txt", 10)


    /**
      * 累加器累加过程
      * 1  现在Executor端进行局部累加
      * 2  将每一个Executor中累加的结果拉取到Driver合并累加加过
      *
      *
      * 累加器只能用来进行累加不能修改
      *
      */

    //定义累加器, 默认值是0
    val acc: LongAccumulator = sc.longAccumulator


    rdd1.foreach(i => {

      //在算子内可以对累加器进行累加

      acc.add(1)

    })


    //在Driver端获取累加器的结果
    println(acc.value)




  }
}
