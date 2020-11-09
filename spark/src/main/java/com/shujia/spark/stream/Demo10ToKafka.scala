package com.shujia.spark.stream

import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo10ToKafka {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[2]")
      .appName("stru")
      .config("spark.sql.shuffle.partitions", 2)
      .config("spark.sql.streaming.checkpointLocation", "spark/data/checkpoint")
      .getOrCreate()

    import org.apache.spark.sql.functions._
    import spark.implicits._


    /**
      * 连接kafka,创建df
      *
      */

    val kafkaDF: DataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "master:9092,node1:9092,node2:9092") //kafka 集群地址
      .option("subscribe", "test_topic1")
      .option("startingOffsets", "latest") // latest : 只读最新的数据,  earliest : 读所有数据
      .load()

    /**
      * root
      * |-- key: binary (nullable = true)
      * |-- value: binary (nullable = true)
      * |-- topic: string (nullable = true)
      * |-- partition: integer (nullable = true)
      * |-- offset: long (nullable = true)
      * |-- timestamp: timestamp (nullable = true)
      * |-- timestampType: integer (nullable = true)
      */


    val df: DataFrame = kafkaDF
      .selectExpr("cast(value as string) as value")
      .filter($"value" === "java")


    df.writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "master:9092,node1:9092,node2:9092")
      .option("topic", "test1") //如果topic不存在, 默认会自动创建一个分区为1 副本为1的topic
      .start()
      .awaitTermination()


  }
}
