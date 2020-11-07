package com.shujia.spark.stream

import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Demo8STreamJoinStream {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[2]")
      .appName("stru")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._

    /**
      * 读取分数表
      *
      */
    val score: DataFrame = spark
      .readStream
      .format("socket")
      .option("host", "master")
      .option("port", 9999)
      .load()

    //分数表
    val scoreDF: DataFrame = score.as[String].map(line => {
      val split: Array[String] = line.split(",")
      val stu_id: String = split(0)
      val cou_id: String = split(1)
      val sco: Integer = split(2).toInt
      (stu_id, cou_id, sco)
    }).toDF("stu_id", "cou_id", "sco")

    /**
      * 读取学生表
      *
      */
    val student: DataFrame = spark
      .readStream
      .format("socket")
      .option("host", "master")
      .option("port", 8888)
      .load()

    //分数表
    val studentDF: DataFrame = student.as[String].map(line => {
      val split: Array[String] = line.split(",")
      val id: String = split(0)
      val name: String = split(1)
      val age: Integer = split(2).toInt
      val gender: String = split(3)
      val clazz: String = split(4)
      (id, name, age, gender, clazz)
    }).toDF("id", "name", "age", "gender", "clazz")


    val joinDF: DataFrame = scoreDF.join(studentDF, $"stu_id" === $"id")

    joinDF.writeStream
      .format("csv")
      .option("checkpointLocation", "spark/data/checkpoint")
      .option("path", "spark/data/csv")
      .start()
      .awaitTermination()


  }
}
