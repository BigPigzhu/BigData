package com.shujia.flink.table

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row


object Demo2KafkaOnMysql {
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

    fsTableEnv.executeSql(
      """
        |CREATE TABLE words (
        | word STRING
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
      * 需要手动在mysqk中创建表

DROP TABLE IF EXISTS `wordcount`;
CREATE TABLE `wordcount` (
  `word` varchar(255) NOT NULL,
  `c` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`word`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

      *
      */

    fsTableEnv.executeSql(
      """
        |
        |CREATE TABLE wordcount (
        |  word STRING,
        |  c BIGINT,
        |  PRIMARY KEY (word) NOT ENFORCED
        |) WITH (
        |   'connector' = 'jdbc',
        |   'url' = 'jdbc:mysql://master:3306/student',
        |   'table-name' = 'wordcount',
        |   'username' = 'root',
        |   'password' = '123456'
        |)
        |
      """.stripMargin)


    /**
      * 执行查询将结果保存到mysql
      */
    fsTableEnv.executeSql(
      """
        |insert into wordcount
        |select word ,count(*) as c from words group by word
        |
      """.stripMargin)


  }
}
