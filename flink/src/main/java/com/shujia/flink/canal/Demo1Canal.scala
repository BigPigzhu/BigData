package com.shujia.flink.canal

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

object Demo1Canal {
  def main(args: Array[String]): Unit = {

    val fsSettings: EnvironmentSettings = EnvironmentSettings
      .newInstance()
      .useBlinkPlanner() //计划器
      .inStreamingMode()
      .build()

    val fsEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val fsTableEnv: StreamTableEnvironment = StreamTableEnvironment.create(fsEnv, fsSettings)


    /**
      * canal-json
      *
      * glink 对接canal-json格式的数据默认就是一个更新的流
      *
      */

    fsTableEnv.executeSql(
      """
        |
        |CREATE TABLE base_category1 (
        |  id BIGINT,
        |  name STRING
        |) WITH (
        | 'connector' = 'kafka',
        | 'topic' = 'gma.base_category1',
        | 'properties.bootstrap.servers' = 'master:9092,node1:9092,node2:9092',
        | 'properties.group.id' = 'testGroup',
        | 'format' = 'canal-json',
        | 'scan.startup.mode' = 'earliest-offset'
        |)
        |
      """.stripMargin)

    fsTableEnv.from("base_category1").toRetractStream[Row].print()


    fsEnv.execute()

  }

}
