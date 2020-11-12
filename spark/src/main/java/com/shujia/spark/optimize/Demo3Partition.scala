package com.shujia.spark.optimize

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object Demo14Partition {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setAppName("Demo13SparkPi")
      .setMaster("local")

    //默认并行度 当使用shuffle类算子的时候起作用
    // conf.set("spark.default.parallelism", "2")


    val sc: SparkContext = new SparkContext(conf)
    /**
      * 默认读取hdfs文件  分区数由文件大小决定  切片规则和mr一致  默认一个block对应一个分区
      * 使用textFile  读取文件的时候可以指定参数提高并行度
      *
      *
      * 默认rdd分区数等于他所依赖的rdd分区数
      * 当使用shuffle类算子的时候可以手动指定分区数   如果不知道默认和父rdd相同
      *
      *
      * 并行度设置规则
      * 1、集群资源  数据量
      * 如果集群资源充足  保证每一个task处理的数据不要太少就行  50m-1G
      *
      * 2、保证task的数量是资源数量的三倍    可以充分利用资源
      *
      */

    val studentRDD: RDD[String] = sc.textFile("java/data/students.txt", 10)

    //获取rdd分区数
    val numPartition: Int = studentRDD.getNumPartitions

    println(s"studentRDD分区数；$numPartition")

    studentRDD.foreachPartition(iter => {
      println("-----------")
      println(iter.toList)
    })


    val kvRDD: RDD[(String, String)] = studentRDD.map(line => {
      val clazz: String = line.split(",")(4)
      (clazz, line)
    })

    println(s"kvRDD分区数；${kvRDD.getNumPartitions}")


    //分组   手动指定分区数
    val groupRDD: RDD[(String, Iterable[String])] = kvRDD.groupByKey(120)


    println(s"groupRDD分区数；${groupRDD.getNumPartitions}")


    val partitionRDD: RDD[(String, Iterable[String])] = kvRDD.groupByKey(new MyPartition)

    println(s"partitionRDD分区数；${partitionRDD.getNumPartitions}")


    /**
      * foreachPartition  将分区一个一个传递进去   action算子
      *
      */
    partitionRDD.foreachPartition(iter => {
      println("-----------")
      println(iter.toList)
    })
  }
}

/**
  * spark默认使用hashf分区
  *
  * 自定义分区
  *
  *
  */

class MyPartition extends Partitioner {

  //分区数
  override def numPartitions: Int = 2

  /**
    *
    * 通过key获取key属于哪一个分区  （属于哪一个reduce）
    */
  override def getPartition(key: Any): Int = {

    //文科一个区 理科一个区

    key.toString.substring(0, 2) match {
      case "文科" => 0
      case "理科" => 1
      case _ => 0
    }
  }
}
