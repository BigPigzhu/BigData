package com.shujia.flink.table

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row
object Demo5EventTImeTopN {
  def main(args: Array[String]): Unit = {

    val fsSettings: EnvironmentSettings = EnvironmentSettings
      .newInstance()
      .useBlinkPlanner() //计划器
      .inStreamingMode()
      .build()

    val fsEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    fsEnv.setParallelism(1)

    val fsTableEnv: StreamTableEnvironment = StreamTableEnvironment.create(fsEnv, fsSettings)



    /*
001,20180503152101
001,20180503152102
002,20180503152101
002,20180503152102
002,20180503152103
003,20180503152101
003,20180503152102
003,20180503152103
003,20180503152104
001,20180503152130
 */
    /**
      * kafka-console-producer.sh --broker-list master:9092,node1:9092,node2:9092 --topic items_event
      *
      */

    /**
      * ts AS TO_TIMESTAMP(time,'yyyyMMddHHmmss')
      * 使用事件事件
      *
      * WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
      *
      *设置数据最大延迟几秒
      *
      */

    fsTableEnv.executeSql(
      """
        |CREATE TABLE items (
        | itemId STRING,
        | t STRING,
        | ts AS TO_TIMESTAMP(t,'yyyyMMddHHmmss'),
        | WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
        |) WITH (
        | 'connector' = 'kafka',
        | 'topic' = 'items_event',
        | 'properties.bootstrap.servers' = 'master:9092,node1:9092,node2:9092',
        | 'properties.group.id' = 'testGroup1',
        | 'format' = 'csv',
        | 'csv.ignore-parse-errors' = 'true',
        | 'scan.startup.mode' = 'latest-offset'
        |)
        |
        |
      """.stripMargin)

/*    val table: Table = fsTableEnv.from("items")
    table.printSchema()
    table.toAppendStream[Row].print()
    fsEnv.execute()*/

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


  }
}
