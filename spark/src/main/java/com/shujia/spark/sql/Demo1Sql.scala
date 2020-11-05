package com.shujia.spark.sql

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

object Demo1Sql {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("sql")

    //saprk core 入口
    val sc = new SparkContext(conf)

    /**
      * 老版api
      *
      */

    val sqlContext = new SQLContext(sc)


    //读取一个json格式的数据,构架df
    //df 是一个由列名和列类型的表结构
    val df: DataFrame = sqlContext.read.json("spark/data/students.json")

    df.groupBy("clazz")
      .count()
      .show()


  }

}
