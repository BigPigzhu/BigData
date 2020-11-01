package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object Demo6FlatMap {
  def main(args: Array[String]): Unit = {

    /**
      * flatmap: 将数据展开， 传入一行返回多行
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val rdd1: RDD[String] = sc.parallelize(List("java,spark", "spark,scala,java", "java"))


    //将数据展开，去掉java

    val rdd2: RDD[String] = rdd1.flatMap(line => {

      val array: mutable.ArrayOps[String] = line
        .split(",")
        .filter(word => !"java".equals(word)) //过滤数据

      //返回一个集合就行
      array
    })


    rdd2.foreach(println)


  }
}
