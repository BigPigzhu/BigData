package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo8GroupByKey {
  def main(args: Array[String]): Unit = {
    /**
      * groupByKey  : 通过key进行分组，将相同的key分到同一个reduce中
      *
      * 会产生shuffle
      *
      */


    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("opt")

    val sc = new SparkContext(conf)

    val linesRDD: RDD[String] = sc.textFile("spark/data/students.txt")


    //将数据转换成kv格式
    val kvRDD: RDD[(String, String)] = linesRDD.map(line => {

      val clazz: String = line.split(",")(4)

      (clazz, line)
    })

    //分组统计班级人数

    val groupRDD: RDD[(String, Iterable[String])] = kvRDD.groupByKey()


    val clazzNum: RDD[(String, Int)] = groupRDD.map(kv => {
      val clazz: String = kv._1

      //这个班级的所有学生
      //迭代器： 只能迭代一次
      val students: Iterable[String] = kv._2

      //转换成list  数据会放到内存中， 如果组内的数据量比较大，会导致内存溢出
      // list  : 可以循环多次
      //val list: List[String] = students.toList


      //统计班级人数
      val num: Int = students.size

      (clazz, num)
    })

    clazzNum.foreach(println)


  }
}
