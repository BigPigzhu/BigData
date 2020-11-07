package com.shujia.spark.stream

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Duration, Durations, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Demo1WordCount {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setMaster("local[2]")// spark streaming 需要指定多个资源,  结束数据会占用一个
      .setAppName("wc")

    val sc = new SparkContext(conf)

    /**
      *
      * 创建spark streaming 上下文对象
      *
      * 指定多久计算一次
      *
      * Durations.seconds(5)  5秒计算一次
      */

    val ssc = new StreamingContext(sc, Durations.seconds(5))

    /**
      * 连接socket获取数据,  创建DStream
      *
      * 需要到master中启动一个socket服务
      *
      *
      * 安装nc
      * yum install nc -y
      *
      * 启动nc
      * nc -lk 9999
      *
      */
    val ds: ReceiverInputDStream[String] = ssc.socketTextStream("master", 9999)

    /**
      * 以下代码每隔5iao执行一次
      *
      */

    val countDS: DStream[(String, Int)] = ds
      .flatMap(_.split(","))
      .map((_, 1))
      .reduceByKey(_ + _)

    //打印结果
    countDS.print()



    //启动spark streaming
    ssc.start()
    ssc.awaitTermination()

  }
}
