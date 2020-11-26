package com.shujia.flink.window

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows
import org.apache.flink.streaming.api.windowing.time.Time

object Demo2SessionWindow {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    /**
      * 会话窗口，会话结束开始计算
      * 一段时间没有数据过来开始计算
      * ProcessingTimeSessionWindows ： 处理时间的会话窗口
      * EventTimeSessionWindows ： 事件事件的会话窗口
      *
      */
    ds.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      .window(ProcessingTimeSessionWindows.withGap(Time.seconds(5)))
      .sum(1)
      .print()


    env.execute()

  }
}
