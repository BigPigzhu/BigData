package com.shujia.flink.transformation

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.scala._

object Demo5Reduce {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(4)

    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    /**
      *
      * reduce： 对同一个key进行聚合，只能再keyBy之后使用
      */

    ds.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      //.reduce((v1, v2) => (v1._1, v1._2 + v2._2))
      .reduce(new ReduceFunction[(String, Int)] {

      override def reduce(value1: (String, Int), value2: (String, Int)): (String, Int) = {
        (value1._1, value1._2 + value2._2)
      }
    })
      .print()


    env.execute()

  }
}
