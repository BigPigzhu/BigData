package com.shujia.spark.sql

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object Demo2SparkSession {

  def main(args: Array[String]): Unit = {

    /**
      * SparkSession: 新版api统一入口,相当于SparkContext
      *
      */

    val spark: SparkSession = SparkSession
      .builder()
      .appName("ss")
      .master("local")
      .getOrCreate()

    //从sparkSession中也可以回到SparkCOntext
    //val sc: SparkContext = spark.sparkContext

    /**
      * DataFrame: 对rdd的一个封装,  加上了列名和列的类型, 所有可以基于df写sql
      *
      */


    //读取数据
    val df: DataFrame = spark
      .read
      .json("spark/data/students.json")

    /**
      * DSL ; 类sql语法, 介于sql和代码之间
      *
      */

    df.groupBy("clazz", "gender") //指定分组列
      .count()
      .show()


    /**
      * 注册临时视图
      *
      */

    df.createOrReplaceTempView("student")

    /**
      * 写sql
      *
      */

    val resultDF: DataFrame = spark.sql(
      """
        |
        |select clazz,gender,count(*) as num from student group by clazz,gender
        |
        |
      """.stripMargin)

    resultDF.show()


  }

}
