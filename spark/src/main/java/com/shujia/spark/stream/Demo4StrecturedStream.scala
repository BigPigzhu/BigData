package com.shujia.spark.stream

import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object Demo4StrecturedStream {

  def main(args: Array[String]): Unit = {


    val spark: SparkSession = SparkSession.builder()
//      .master("local[2]")
      .appName("stru")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()


    import spark.implicits._
    import org.apache.spark.sql.functions._

    /**
      * 连接socket创建df
      *
      */
    val df: DataFrame = spark
      .readStream
      .format("socket")
      .option("host", "master")
      .option("port", 9999)
      .load()

    //将数据转换成String
    val ds: Dataset[String] = df.as[String]

    val ds1: DataFrame = ds
      .flatMap(_.split(","))
      .select($"value" as "word")

    /**
      * DSL
      *
      */

    //    val counyDS: DataFrame = ds1
    //      .groupBy($"word")
    //      .agg(count($"word") as "c")

    /**
      *
      * sql
      */

    ds1.createOrReplaceTempView("words")

    val counyDS: DataFrame = spark.sql(
      """
        |
        |select word,count(*) from words group by word
        |
      """.stripMargin)


    counyDS
      .writeStream // 输入结果
      .outputMode(OutputMode.Complete()) //
      .format("console") //输出到控制台
      .start()
      .awaitTermination()


  }
}
