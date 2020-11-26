package com.shujia.flink.transformation

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time


object Demo7Window {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val ds1: DataStream[String] = env.socketTextStream("master", 8888)

    /**
      * 统计每隔单词最近5秒的数量
      *
      */

    ds1.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      .timeWindow(Time.seconds(5)) //时间滚动窗口
      .sum(1)
      .print()


    env.execute()

  }
}
