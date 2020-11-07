package com.shujia.spark.stream

import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo7Join {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .master("local[2]")
      .appName("stru")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._


    /**
      * 流表join维表
      *
      *
      */

    val student: DataFrame = spark
      .read
      .format("csv")
      .option("sep", ",")
      .schema("id string , name string ,age int , gender string ,clazz string")
      .load("spark/data/students.txt")


    val score: DataFrame = spark
      .readStream
      .format("socket")
      .option("host", "master")
      .option("port", 9999)
      .load()

    val scoreDF: DataFrame = score.as[String].map(line => {
      val split: Array[String] = line.split(",")
      val stu_id: String = split(0)
      val cou_id: String = split(1)
      val sco: Integer = split(0).toInt
      (stu_id, cou_id, sco)
    }).toDF("stu_id", "cou_id", "sco")


    /**
      * join
      *
      * 维表可以缓存或者广播
      *
      */

    //    student.cache()

    val joinDF: DataFrame = scoreDF.join(student.hint("broadcast"), $"stu_id" === $"id")


    joinDF.writeStream
      .format("console")
      .outputMode(OutputMode.Append())
      .start()
      .awaitTermination()


  }
}
