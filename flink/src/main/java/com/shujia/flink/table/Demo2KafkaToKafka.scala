package com.shujia.flink.table

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._


object Demo2KafkaToKafka {
  def main(args: Array[String]): Unit = {


    val fsSettings: EnvironmentSettings = EnvironmentSettings
      .newInstance()
      .useBlinkPlanner() //计划器
      .inStreamingMode()
      .build()

    val fsEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val fsTableEnv: StreamTableEnvironment = StreamTableEnvironment.create(fsEnv, fsSettings)


    /**
      * kafka-console-producer.sh --broker-list master:9092,node1:9092,node2:9092 --topic word
      *
      */
    //source 表
    //ts AS PROCTIME()   增加处理时间字段

    fsTableEnv.executeSql(
      """
        |CREATE TABLE words (
        | word STRING,
        | ts AS PROCTIME()
        |) WITH (
        | 'connector' = 'kafka',
        | 'topic' = 'word',
        | 'properties.bootstrap.servers' = 'master:9092,node1:9092,node2:9092',
        | 'properties.group.id' = 'testGroup',
        | 'format' = 'csv',
        | 'scan.startup.mode' = 'earliest-offset'
        |)
        |
      """.stripMargin)

    /**
      * sink 表
      *
      */

    fsTableEnv.executeSql(
      """
        |
        |CREATE TABLE sink_table (
        | word STRING,
        | end_time TIMESTAMP(3),
        |  c bigint
        |) WITH (
        | 'connector' = 'kafka',
        | 'topic' = 'sink_topic',
        | 'properties.bootstrap.servers' = 'master:9092,node1:9092,node2:9092',
        | 'format' = 'csv'
        |)
        |
      """.stripMargin)


    /**
      * 统计每隔单词最近5秒的数量
      *
      * TUMBLE(ts, INTERVAL '5' SECONDS) 指定窗口的大小和时间
      * TUMBLE_END ： 获取窗口结束时间
      * TUMBLE_STATRt : 窗口开始时间
      *
      */

    /**
      * 执行查询将结果保存到mysql
      */
    fsTableEnv.executeSql(
      """
        |insert into sink_table
        |select
        | word,
        | TUMBLE_END(ts, INTERVAL '5' SECONDS) as end_time,
        | count(*) as c
        | from words
        |group by word,TUMBLE(ts, INTERVAL '5' SECONDS)
        |
      """.stripMargin)

    /**
      *
      * kafka-console-consumer.sh --bootstrap-server  master:9092,node1:9092,node2:9092  --from-beginning --topic sink_topic
      *
      */


  }
}
