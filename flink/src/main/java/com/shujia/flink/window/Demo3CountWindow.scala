package com.shujia.flink.window

import org.apache.flink.streaming.api.scala._

object Demo3CountWindow {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val ds: DataStream[String] = env.socketTextStream("master", 8888)


    /**
      * .countWindow(10)  : 没10条数据计算一次
      * countWindow(10, 2)： 每隔两条数据计算一次，计算最近10条数据
      *
      */
    ds.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      //      .countWindow(10)
      .countWindow(10, 2)
      .sum(1)
      .print()


    env.execute()


  }

}
