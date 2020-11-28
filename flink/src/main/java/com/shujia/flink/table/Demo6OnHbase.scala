package com.shujia.flink.table

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

object Demo6OnHbase {
  def main(args: Array[String]): Unit = {

    val fsSettings: EnvironmentSettings = EnvironmentSettings
      .newInstance()
      .useBlinkPlanner() //计划器
      .inStreamingMode()
      .build()

    val fsEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    fsEnv.setParallelism(1)

    val fsTableEnv: StreamTableEnvironment = StreamTableEnvironment.create(fsEnv, fsSettings)


    fsTableEnv.executeSql(
      """
        |
        |CREATE TABLE mysql_student (
        |  id INT,
        |  name STRING,
        |  age INT,
        |  gender STRING,
        |  clazz STRING
        |) WITH (
        |   'connector' = 'jdbc',
        |   'url' = 'jdbc:mysql://master:3306/student',
        |   'table-name' = 'student',
        |   'username' = 'root',
        |   'password' = '123456'
        |)
        |
      """.stripMargin)


    fsTableEnv.executeSql(
      """
        |
        |CREATE TABLE student (
        | id INT,
        | info ROW<name STRING,age INT , gender STRING, clazz STRING>,
        | PRIMARY KEY (id) NOT ENFORCED
        |) WITH (
        | 'connector' = 'hbase-1.4',
        | 'table-name' = 'student',
        | 'zookeeper.quorum' = 'master:2181,node1,2181,node2:2181'
        |)
        |
      """.stripMargin)


    fsTableEnv.executeSql(
      """
        |insert into student
        |select id,ROW(name,age,gender,clazz) as info from mysql_student where clazz='文科一班'
        |
      """.stripMargin)


  }
}
