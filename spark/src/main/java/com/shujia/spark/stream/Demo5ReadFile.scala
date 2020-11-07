package com.shujia.spark.stream

import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo5ReadFile {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[2]")
      .appName("stru")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()


    import spark.implicits._
    import org.apache.spark.sql.functions._


    val student: DataFrame = spark
      .readStream
      .format("csv")
      .option("sep", ",")
      .schema("id STRING ,name STRING , age  int ,gender string ,clazz string")
      .load("spark/data/stu") //监控一个目录构建流


    student
      .writeStream
      .format("console")
      .outputMode(OutputMode.Append())
      .start()
      .awaitTermination()


  }
}
