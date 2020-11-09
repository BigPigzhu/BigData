package com.shujia.spark.stream

import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo12SumPic {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .master("local[2]")
      .appName("stru")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._


    /**
      * 连接kafka,创建df
      *
      */

    val kafkaDF: DataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "master:9092,node1:9092,node2:9092") //kafka 集群地址
      .option("subscribe", "item")
      .option("startingOffsets", "earliest") // latest : 只读最新的数据,  earliest : 读所有数据
      .load()


    /**
      * 实时统计交易额
      *
      */

    val valueDF: DataFrame = kafkaDF
      .selectExpr("cast(value as string)")
      .as[String]
      .map(line => {
        val split: Array[String] = line.split(",")
        val id: String = split(0)
        val time: String = split(1)
        val pic: Double = split(2).toDouble
        (id, time, pic)
      }).toDF("id", "time", "pic")


    //注册成临时视图
    valueDF.createOrReplaceTempView("item")


    val sumPic: DataFrame = spark.sql(
      """
        |
        |select sum(pic) from item
        |
        |
      """.stripMargin)


    sumPic.writeStream
      .format("console")
      .outputMode(OutputMode.Complete())
      .start()
      .awaitTermination()


  }
}
