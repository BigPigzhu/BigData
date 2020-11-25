package com.shujia.flink.transformation

import org.apache.flink.streaming.api.scala._

object Demo4KeyBy {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(4)

    val ds: DataStream[String] = env.socketTextStream("master", 8888)



    ds.flatMap(_.split(","))
      .keyBy(k => k)
      .print()

    env.execute()

  }
}
