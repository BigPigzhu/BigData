package com.shujia.flink.source

import org.apache.flink.streaming.api.scala._

object Demo4TestMysqlSource {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(1)
    /**
      * 使用自定义mysqlsource
      *
      */


    val ds: DataStream[String] = env.addSource(new Demo3MysqlSource)


    ds.map(_.split("\t")(4))
      .map((_, 1))
      .keyBy(_._1)
      .sum(1)
      .print()


    env.execute()


  }
}
