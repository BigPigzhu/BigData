package com.shujia.spark.sql

import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo12UDF {
  def main(args: Array[String]): Unit = {

    /**
      * udt ； 参入一行返回一行
      *
      */

    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("udf")
      .getOrCreate()


    /**
      * 注册自定义函数
      *
      */

    spark.udf.register("stringLength", (str: String) => str.length)


    val df: DataFrame = spark.read
      .format("json")
      .load("spark/data/students.json")

    /**
      * DSL
      *
      */
    df.selectExpr("stringLength(id) as len")
      .show()

    /**
      *
      * sql
      */


    df.createOrReplaceTempView("student")


    spark.sql(
      """
        |
        |select stringLength(id) from student
        |
      """.stripMargin)
      .show()


  }
}
