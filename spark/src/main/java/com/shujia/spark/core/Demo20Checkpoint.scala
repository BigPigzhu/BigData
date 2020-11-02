package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object Demo20Checkpoint {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local[8]").setAppName("opt")

    val sc = new SparkContext(conf)

    //设置checkpoint的路径
    sc.setCheckpointDir("spark/data/checkpoint")


    //读取分数表
    val studentRDD: RDD[String] = sc.textFile("spark/data/students.txt")


    val stuRDD: RDD[String] = studentRDD.map(line => {
      println("map...")
      line
    })


    /**
      * checkpoint : 快照,  将rdd的数据持久化到hdfs, 数据不会丢失
      *
      * 需要在sparkCOntext中设置  checkpoint的路径
      *
      *
      * 当第一个job计算完成之后,从追后一个rdd向前回溯,  将做了ckeckpoint的rdd打赏标记
      * 另启动一个job重新计算rdd的数据,将rdd的数据爆粗难道hdfs
      *
      *
      * 优化: 在checkppoint之前将rdd的数据缓存起来
      *
      *
      *
      * checkpoint 会切断rdd的依赖关系
      *
      */


    stuRDD.cache()

    stuRDD.checkpoint()



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


    stuRDD
      .map(line => (line.split(",")(3), 1))
      .reduceByKey(_ + _)
      .foreach(println)


    while (true) {

    }

  }

}
