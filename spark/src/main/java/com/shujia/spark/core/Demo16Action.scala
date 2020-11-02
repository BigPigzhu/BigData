package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo16Action {

  def main(args: Array[String]): Unit = {

    /**
      * action算子,  每一个action算子都会触发一个job任务
      *
      */

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)


    val student: RDD[String] = sc.textFile("spark/data/students.txt")

    /**
      * foreach :遍历数据
      *
      */

    student.foreach(println)

    student.foreach(println)


    /**
      * count ;统计行数
      *
      */

    val count: Long = student.count()

    println(count)

    /**
      * reduce;全局聚合
      * 由预聚合
      *
      */

    val value: RDD[Int] = student.map(i => 1)

    val reduce: Int = value.reduce((x, y) => x + y)

    println(reduce)

    /**
      * save: 保存数据
      *
      */

    student.saveAsTextFile("spark/data/out3")


    // 序列化输出
    student.saveAsObjectFile("spark/data/out4")


    println("=" * 100)

    val rdd4: RDD[Nothing] = sc.objectFile("spark/data/out4")

    rdd4.foreach(println)


    while (true) {

    }


  }

}
