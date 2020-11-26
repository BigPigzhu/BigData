package com.shujia.flink.window

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.{SlidingProcessingTimeWindows, TumblingProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.time._

object Demo1TimeWindow {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment


    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    /**
      * 时间窗口
      * TumblingProcessingTimeWindows ： 滚动处理时间窗口
      * TumblingEventTimeWindows ： 滚动事件时间窗口
      *
      * SlidingEventTimeWindows: 华东事件时间窗口
      * SlidingProcessingTimeWindows; 华东处理时间窗口
      *
      *
      */


    ds.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      //      .timeWindow(Time.seconds(5))
      //      .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
      .window(SlidingProcessingTimeWindows.of(Time.seconds(15), Time.seconds(5)))
      .sum(1)
      .print()


    env.execute()


  }

}
