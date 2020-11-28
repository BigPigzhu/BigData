package com.shujia.flink.table

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.table.api._

import org.apache.flink.types.Row


object Demo1TableApi {
  def main(args: Array[String]): Unit = {


    val fsSettings: EnvironmentSettings = EnvironmentSettings
      .newInstance()
      .useBlinkPlanner() //计划器
      .inStreamingMode()
      .build()

    val fsEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    /**
      * flink  table api  环境
      *
      *
      */
    val fsTableEnv: StreamTableEnvironment = StreamTableEnvironment.create(fsEnv, fsSettings)

    /**
      *
      * executeSql : 执行一条sql语句，可以执行DDl   insert into
      * from : 返回一个表的table对象
      * sqlQuery ： 执行一条查询语句返回一个table对象
      *
      */

    /**
      *
      * kafka-console-producer.sh --broker-list master:9092,node1:9092,node2:9092 --topic student
      *
      *
      */

    /**
      * 创建一个source表
      *
      */
    fsTableEnv.executeSql(
      """
        |CREATE TABLE student (
        | id STRING,
        | name STRING,
        | age int,
        | gender STRING,
        | clazz STRING
        |) WITH (
        | 'connector' = 'kafka',
        | 'topic' = 'student',
        | 'properties.bootstrap.servers' = 'master:9092,node1:9092,node2:9092',
        | 'properties.group.id' = 'testGroup',
        | 'format' = 'csv',
        | 'scan.startup.mode' = 'earliest-offset'
        |)
        |
      """.stripMargin)


    val table: Table = fsTableEnv.from("student")

    table.printSchema()

    //table.toAppendStream[Row].print()

    val countTable: Table = table
      .groupBy($"clazz")
      .select($"clazz", $"clazz".count())

    /**
      * toAppendStream ： 追加的流
      * toRetractStream : 更新的流
      *
      */
    //countTable.toRetractStream[Row].print()


    val resultTable: Table = fsTableEnv.sqlQuery(
      """
        |
        |select clazz,count(*) from student group by clazz
        |
        |
      """.stripMargin)

    resultTable.toRetractStream[Row].print()

    fsEnv.execute()


  }
}
