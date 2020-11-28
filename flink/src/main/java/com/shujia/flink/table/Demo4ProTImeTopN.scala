package com.shujia.flink.table

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

object Demo4ProTImeTopN {
  def main(args: Array[String]): Unit = {

    val fsSettings: EnvironmentSettings = EnvironmentSettings
      .newInstance()
      .useBlinkPlanner() //计划器
      .inStreamingMode()
      .build()

    val fsEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val fsTableEnv: StreamTableEnvironment = StreamTableEnvironment.create(fsEnv, fsSettings)



    /*
itemid,c
001,1
001,1
001,1
002,1
002,1
003,1
003,1
003,1
003,1
001,1
001,1
001,1
002,1
003,1
001,1
002,1
003,1
 */
    /**
      * kafka-console-producer.sh --broker-list master:9092,node1:9092,node2:9092 --topic items
      *
      */

    fsTableEnv.executeSql(
      """
        |CREATE TABLE items (
        | itemId STRING,
        | c bigint ,
        | ts AS PROCTIME()
        |) WITH (
        | 'connector' = 'kafka',
        | 'topic' = 'items',
        | 'properties.bootstrap.servers' = 'master:9092,node1:9092,node2:9092',
        | 'properties.group.id' = 'testGroup',
        | 'format' = 'csv',
        | 'scan.startup.mode' = 'earliest-offset'
        |)
        |
        |
      """.stripMargin)

    fsTableEnv.executeSql(
      """
        |
        |CREATE TABLE topn (
        |  itemId STRING,
        |  c BIGINT,
        |  end_time STRING,
        |  r BIGINT,
        |  PRIMARY KEY (end_time,r) NOT ENFORCED
        |) WITH (
        |   'connector' = 'jdbc',
        |   'url' = 'jdbc:mysql://master:3306/student',
        |   'table-name' = 'topn',
        |   'username' = 'root',
        |   'password' = '123456'
        |)
        |
      """.stripMargin)


    /**
      *
      * 统计最新15秒最热门的前两个商品，每隔5秒统计一次
      *
      */

  fsTableEnv.executeSql(
      """
        |insert into topn
        |select itemId,c,end_time,r from
        |(
        |select itemId,c,end_time,row_number() over(partition by end_time order by c desc ) as r  from
        |(
        |select itemId,DATE_FORMAT(HOP_END(ts ,INTERVAL '5' SECONDS, INTERVAL '15' SECONDS),'yyyy-MM-dd hh:mm:ss') as end_time,count(*) c from items
        |group by itemId,HOP(ts ,INTERVAL '5' SECONDS, INTERVAL '15' SECONDS)
        |) as a
        |) as b
        |where r < 3
        |
      """.stripMargin)


    /**
      *
      * flink sql 运行方式
      * 1、 可以直接将sql复制到sql-client中运行
      * 2、 将项目打包到集群中运行
      * flink run -c com.shujia.flink.table.Demo4ProTImeTopN -p 1 flink-1.0.jar
      *
      */

  }
}
