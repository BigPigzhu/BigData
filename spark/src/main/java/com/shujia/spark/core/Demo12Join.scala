package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo12Join {

  def main(args: Array[String]): Unit = {

    /**
      *
      * join: 连接
      *
      */


    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val rdd1: RDD[(String, String)] = sc.parallelize(List(("001", "张三"), ("002", "李四"), ("003", "王五"), ("004", "晓伟")))


    val rdd2: RDD[(String, Int)] = sc.parallelize(List(("001", 23), ("002", 24), ("003", 25), ("003", 25)))


    //join ；默认 innerJoin
    val joinRDD: RDD[(String, (String, Int))] = rdd1.join(rdd2)


    joinRDD.foreach(println)


    //leftOuterJoin  ： 以做表为基础
    val rdd3: RDD[(String, (String, Option[Int]))] = rdd1.leftOuterJoin(rdd2)

    //(003,(王五,Some(25)))

    val resultRDD: RDD[(String, String, Int)] = rdd3.map(kv => {
      val id: String = kv._1

      val name: String = kv._2._1

      //如果没有关联上，返回默认值
      val age: Int = kv._2._2.getOrElse(0)

      (id, name, age)
    })

    resultRDD.foreach(println)

  }

}
