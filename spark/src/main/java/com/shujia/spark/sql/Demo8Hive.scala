package com.shujia.spark.sql

import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo8Hive {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .appName("topn")
      .config("spark.sql.shuffle.partitions", 2)
      .enableHiveSupport() //使用hive的元数据
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._


    /**
      * 整合好hive之后在代码中也可以直接使用hive中的表
      *
      */

    spark.sql(
      """
        |select * from student
        |
      """.stripMargin).show()

    //扫描表
    val df: DataFrame = spark.table("student")

    df.show()
  }
}
