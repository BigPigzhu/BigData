package com.shujia.spark.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

object Demo19Cache {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local[8]").setAppName("opt")

    val sc = new SparkContext(conf)


    //读取分数表
    val studentRDD: RDD[String] = sc.textFile("spark/data/students.txt")


    val stuRDD: RDD[String] = studentRDD.map(line => {
      println("map...")
      line
    })

    /**
      *
      * 对多次使用的rdd进行缓存
      *
      * cache 也是懒执行, 当第一个job执行的时候会将数据缓存起来
      * 后面job执行的时候就可以直接从缓存中获取数据
      *
      *
      * 缓存的数据在任务结束节后自动删除
      */

    //.persist(StorageLevel.MEMORY_ONLY)  简写
    //stuRDD.cache()


    //缓存到内存,内存足够第一选择
    stuRDD.persist(StorageLevel.MEMORY_ONLY)

    // 第二选择
    //stuRDD.persist(StorageLevel.MEMORY_ONLY_SER)

    //第三选择
    //stuRDD.persist(StorageLevel.MEMORY_AND_DISK_SER)


    // 统计i班级人数
    stuRDD
      .map(line => (line.split(",")(4), 1))
      .reduceByKey(_ + _)
      .foreach(println)


    // 统计性别人数
    stuRDD
      .map(line => (line.split(",")(3), 1))
      .reduceByKey(_ + _)
      .foreach(println)


    while (true){

    }

  }

}
