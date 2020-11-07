package com.shujia.spark.stream

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

object Demo3Window {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setMaster("local[2]") // spark streaming 需要指定多个资源,  结束数据会占用一个
      .setAppName("wc")

    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Durations.seconds(5))
    //设置checkpoint路径
    ssc.checkpoint("spark/data/checkpoint")

    val ds: ReceiverInputDStream[String] = ssc.socketTextStream("master", 9999)


    /**
      *
      * 有状态算子
      *
      * 将每一个统计单词的数量看着是一个状态(数值)
      * 后面的计算不停的去更新之前的状态
      *
      */

    val kvDS: DStream[(String, Int)] = ds.flatMap(_.split(","))
      .map((_, 1))

    /**
      *
      * 窗口操作
      *
      * 计算最近一段时间的数据, 每隔一段时间计算一次
      *
      * 窗口大小和华东时间必须是,  spark batch是的整数倍
      *
      */

    //    val countDS: DStream[(String, Int)] = kvDS.reduceByKeyAndWindow(
    //      (x: Int, y: Int) => x + y, //聚合函数
    //      Durations.seconds(15), // 窗口大小
    //      Durations.seconds(5) // 多久季赛那一次 (滑动时间)
    //    )

    /**
      * 优化版本
      *
      * 如果窗口由重叠部分,spark streaming 会重复计算
      *
      */

    val countDS: DStream[(String, Int)] = kvDS.reduceByKeyAndWindow(
      (x: Int, y: Int) => x + y, //聚合函数
      (i: Int, j: Int) => i - j, // 减去多余部分的函数
      Durations.seconds(15), // 窗口大小
      Durations.seconds(5) // 多久季赛那一次 (滑动时间)
    )

    //去掉空数据
    val filterDS: DStream[(String, Int)] = countDS.filter(_._2 != 0)


    countDS.print()


    ssc.start()
    ssc.awaitTermination()

  }
}
