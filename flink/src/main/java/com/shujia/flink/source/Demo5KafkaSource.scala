package com.shujia.flink.source

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

object Demo5KafkaSource {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val properties: Properties = new Properties()
    properties.setProperty("bootstrap.servers", "master:9092,node1:9092,node2:9092")
    properties.setProperty("group.id", "test2")
    properties.setProperty("auto.offset.reset", "earliest") //读最早的数据


    /**
      *
      * 控制台生产者
      * kafka-console-producer.sh --broker-list master:9092,node1:9092,node2:9092 --topic flink_topic
      *
      */

    //创建flink 读取kafka的source
    val flinkKafkaConsumer: FlinkKafkaConsumer[String] = new FlinkKafkaConsumer[String](
      "flink_topic",
      new SimpleStringSchema(),
      properties
    )

    //flinkKafkaConsumer.setStartFromEarliest(); // 尽可能从最早的记录开始
    // flinkKafkaConsumer.setStartFromLatest();       // 从最新的记录开始
    // flinkKafkaConsumer.setStartFromTimestamp(...); // 从指定的时间开始（毫秒）
    flinkKafkaConsumer.setStartFromGroupOffsets(); // 默认的方法*/


    val ds: DataStream[String] = env.addSource(flinkKafkaConsumer)


    ds.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      .sum(1)
      .print()

    env.execute()


  }
}
