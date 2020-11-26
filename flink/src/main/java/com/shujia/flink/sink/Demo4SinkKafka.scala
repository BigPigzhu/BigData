package com.shujia.flink.sink

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer

object Demo4SinkKafka {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    val myProducer: FlinkKafkaProducer[String] = new FlinkKafkaProducer[String](
      "master:9092,node1:9092,node2:9092", // broker 列表
      "flink_topic", // 目标 topic
      new SimpleStringSchema()) // 序列化 schema


    ds.addSink(myProducer)


    env.execute()

    /**
      *
      * kafka-console-consumer.sh --bootstrap-server  master:9092,node1:9092,node2:9092  --from-beginning --topic flink_topic
      *
      */

  }

}
