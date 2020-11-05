package com.shujia.spark.sql

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Demo6Save {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("topn")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()

    import spark.implicits._

    val df: DataFrame = spark
      .read
      .format("csv")
      .option("sep", ",")
      .schema("id STRING , name STRING , age INT , gender STRING , clazz STRING")
      .load("spark/data/students.txt")

    /**
      *
      * 1  保存为普通文本文件
      */

    /**
      * SaveMode
      * Append ; 追加
      * Overwrite : 覆盖
      * ErrorIfExists : 存在就报错
      * Ignore : 存在就忽略
      */
    df
      .write
      .format("csv") //保存的模式
      .option("sep", "\t") // 数据分割方式
      .mode(SaveMode.Overwrite)
      .save("spark/data/out8") //保存的路径


    /**
      * 保存为json文件
      *
      */

    df.write
      .format("json")
      .mode(SaveMode.Overwrite)
      .save("spark/data/out9")

    /**
      *
      * 保存为parquet 格式
      * h会对数据进行压缩
      * 可以完美兼容hive
      * 而且数据中自带列名
      *
      */

    df.write
      .mode(SaveMode.Overwrite)
      .parquet("spark/data/out10")

    /**
      * 读取parquet
      * 会自动解析数据中的列名个列的类型
      *
      */

    val df1: DataFrame = spark.read.parquet("spark/data/out10")

    //获取列的描述
    df1.printSchema()


    /**
      * 保存到jdbc
      *
      */

    df.write
      .format("jdbc")
      .mode(SaveMode.Append)
      .option("createTableColumnTypes", "id varchar(255), name varchar(255)") //指定字段类型
      .option("url", "jdbc:mysql://master:3306?useUnicode=true&characterEncoding=UTF-8")
      .option("dbtable", "student.student")
      .option("user", "root")
      .option("password", "123456")
      .save()


  }
}
