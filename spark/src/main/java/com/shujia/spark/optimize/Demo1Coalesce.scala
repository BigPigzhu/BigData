package com.shujia.spark.optimize

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo1Coalesce {
  def main(args: Array[String]): Unit = {


    /**
      * coalesce   重分区
      *
      * 改变rdd的并行度,  如果资源充足,可以提高并行度提高任务执行速度
      *
      * 保证每一个task处理的数据再50m到1G不等
      *
      *
      */

    val conf: SparkConf = new SparkConf()
      .setAppName("app")
      .setMaster("local[1]")
    val sc: SparkContext = new SparkContext(conf)

    //产生小文件
    sc
      .textFile("spark/data/students.txt")

      //重分区   会产生shuffle
      .repartition(2)
    //.saveAsTextFile("spark/data/repartition")


    //数据里面由很多小文件，导致rdd分区很多，每隔分区数据量很小
    val rdd: RDD[String] = sc.textFile("spark/data/repartition")

    println("rdd:" + rdd.getNumPartitions)

    /**
      * repartition  会产生shuffle
      *
      */
    val rdd2: RDD[String] = rdd.repartition(3)

    println("rdd2:" + rdd2.getNumPartitions)


    /**
      * 增啊分区必须产生shuffle
      * 减少分区的时候可以不产生shuflle
      */

    //减少分区的时候可以不产生shuflle
    val cRDD: RDD[String] = rdd2.coalesce(2, shuffle = false)

    println("cRDD:" + cRDD.getNumPartitions)


    cRDD.foreach(println)

    //增加分区必须由shuffle
    val rdd3: RDD[String] = rdd.coalesce(200, shuffle = true)

    println(rdd3.getNumPartitions)

  }
}
