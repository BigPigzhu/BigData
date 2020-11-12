package com.shujia.spark.sql

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions

object Demo13UDAF {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("udf")
      .getOrCreate()

    val df: DataFrame = spark.read
      .format("json")
      .load("spark/data/students.json")


    /**
      *
      * UDAF ; 自定义聚合函数
      *
      */

    spark.udf.register("myCount", new StringCount)


    df.createOrReplaceTempView("student")


    spark.sql(
      """
        |select clazz,myCount(clazz) from student group by clazz
        |
      """.stripMargin)
      .show()


  }
}
