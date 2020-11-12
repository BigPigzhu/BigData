package com.shujia.spark.optimize

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object MapJoin {
  /**
    * map join
    *
    * 将小表广播，大表使用map算子
    *
    * 1、小表不能太大， 不能超过2G
    * 2、如果driver内存不足，需要手动设置  如果广播变量大小超过了driver内存大小，会出现oom
    *
    */

  def main(args: Array[String]): Unit = {


    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("app")
    val sc: SparkContext = new SparkContext(conf)

    //RDD 不能广播
    val studentRDD: RDD[String] = sc.textFile("spark/data/students.txt")

    //将数据拉去到driver端，变成一个map集合
    val stuMap: Map[String, String] = studentRDD
      .collect()//将rdd的数据拉取Driver端变成一个数组
      .map(s => (s.split(",")(0), s))
      .toMap

    //广播map集合
    val broStu: Broadcast[Map[String, String]] = sc.broadcast(stuMap)

    val scoreRDD: RDD[String] = sc.textFile("spark/data/score.txt")

    //循环大表，通过key获取小表信息
    scoreRDD.map(s => {
      val sId: String = s.split(",")(0)

      //重广播变量里面获取数据
      val stuInfo: String = broStu.value.getOrElse(sId, "")

      stuInfo + "," + s
    }).foreach(println)

    while (true) {

    }


  }
}

