package com.shujia.spark.stream

import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo6EventTime {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[2]")
      .appName("stru")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()

    /*

    001,1604731574095,1
001,1604731575095,1
001,1604731576095,1
001,1604731577095,1
001,1604731578095,1
001,1604731579095,1
001,1604731580095,1
001,1604731581095,1
002,1604731574095,1
002,1604731575095,1
002,1604731576095,1
002,1604731577095,1
002,1604731578095,1
002,1604731579095,1
002,1604731580095,1
002,1604731581095,1


     */


    import spark.implicits._
    import org.apache.spark.sql.functions._

    val df: DataFrame = spark
      .readStream
      .format("csv")
      .option("sep", ",")
      .schema("userId string, ts string, type  int")
      .load("spark/data/data")

    /**
      * to_timestamp ; 将字符串转换成时间戳
      * window :  首先将一天划分出多个窗口,  数据落到哪一个窗口,就在哪一个窗口中进行统计
      *
      * 事件时间: 数据中自带的时间
      *
      */

    val countDS: DataFrame = df
      .select($"userId", to_timestamp($"ts", "yyyy-MM-dd HH:mm:ss") as "ts", $"type")
      .groupBy(
        window($"ts", "5 hour", "2 hour"),
        $"userId"
      ).agg(count($"userId"))


    countDS.writeStream
      .format("console")
      .outputMode(OutputMode.Update())
      .start()
      .awaitTermination()


  }
}
