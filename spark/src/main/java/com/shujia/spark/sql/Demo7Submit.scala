package com.shujia.spark.sql

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Demo7Submit {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .appName("topn")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._

    val df: DataFrame = spark
      .read
      .format("csv")
      .option("sep", ",")
      .schema("id STRING , name STRING , age INT , gender STRING , clazz STRING")
      .load("/data/students.txt") //指定hdfs文件路径


    val df1: DataFrame = df.groupBy($"clazz")
      .agg(count($"clazz") as "c")


    df1.write
      .format("csv")
      .option("sep", "\t")
      .mode(SaveMode.Overwrite)
      .save("/data/out5")


  }
}
