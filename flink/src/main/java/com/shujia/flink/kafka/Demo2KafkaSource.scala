package com.shujia.flink.kafka

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend
import org.apache.flink.runtime.state.StateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

object Demo2KafkaSource {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment



    // 每 1000ms 开始一次 checkpoint
    env.enableCheckpointing(10000)

    // 高级选项：

    // 设置模式为精确一次 (这是默认值)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)

    // 确认 checkpoints 之间的时间会进行 500 ms
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500)

    // Checkpoint 必须在一分钟内完成，否则就会被抛弃
    env.getCheckpointConfig.setCheckpointTimeout(60000)

    // 开启在 job 中止后仍然保留的 externalized checkpoints
    env.getCheckpointConfig.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)

    // 同一时间只允许一个 checkpoint 进行
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)


    //enableIncrementalCheckpointing : 开启增量快照
    // 创建状态后端， rocksDB
    val backend: StateBackend = new RocksDBStateBackend("hdfs://master:9000/flink/checkpoints", true)

    //设置状态后端
    env.setStateBackend(backend)


    val properties: Properties = new Properties()
    properties.setProperty("bootstrap.servers", "master:9092,node1:9092,node2:9092")
    properties.setProperty("group.id", "test2")


    /**
      *
      * 控制台生产者
      * kafka-console-producer.sh --broker-list master:9092,node1:9092,node2:9092 --topic flink_test
      *
      */

    //创建flink 读取kafka的source
    val flinkKafkaConsumer: FlinkKafkaConsumer[String] = new FlinkKafkaConsumer[String](
      "flink_test",
      new SimpleStringSchema(),
      properties
    )

    //flinkKafkaConsumer.setStartFromEarliest(); // 尽可能从最早的记录开始
    //flinkKafkaConsumer.setStartFromLatest(); // 从最新的记录开始
    // flinkKafkaConsumer.setStartFromTimestamp(...); // 从指定的时间开始（毫秒）
    flinkKafkaConsumer.setStartFromGroupOffsets(); // 默认的方法


    val ds: DataStream[String] = env.addSource(flinkKafkaConsumer)


    ds.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      .sum(1)
      .print()

    env.execute()


  }
}
