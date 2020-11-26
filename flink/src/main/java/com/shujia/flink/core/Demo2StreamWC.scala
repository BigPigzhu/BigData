package com.shujia.flink.core

import org.apache.flink.streaming.api.scala._

object Demo2StreamWC {
  def main(args: Array[String]): Unit = {


    /**
      * 创建流处理的环境
      *
      */
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    //并行度
    //env.setParallelism(2)

    //读取数据
    //nc -lk 8888
    val linesDS: DataStream[String] = env.socketTextStream("master", 8888)


    val countDS: DataStream[(String, Int)] = linesDS
      .flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      .sum(1) // 默认就是有状态计算


    //打印
    countDS.print()


    //触发任务执行
    env.execute()

  }

}
