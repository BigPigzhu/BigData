package com.shujia.spark.sql

import org.apache.spark.sql.{DataFrame, SparkSession}

object DEmo11SqlBro {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession
      .builder()
      .appName("df")
      .master("local")
      .getOrCreate()

    //大投入隐式转换
    import spark.implicits._
    val studentDF: DataFrame = spark
      .read
      .format("csv")
      .option("sep", ",")
      .schema("id STRING , name STRING , age INT , gender STRING , clazz STRING")
      .load("spark/data/students.txt")

    val scoreDF: DataFrame = spark
      .read
      .format("csv")
      .option("sep", ",")
      .schema("s_id STRING , c_id STRING , sco INT")
      .load("spark/data/score.txt")


    /**
      * map join : 不会产生shuffle
      * 小表的数据量不能太大,  在几十M左右
      *
      * 将小表加载的内存(加载到Executor的内存)  ,将小表广播
      *
      * 广播变量只能广播Driver端的一个集合,所以需要先将表拉取到Driver端(collect)
      *
      * Driver端默认内存是1G  , 如果小表的数据流量超过1g导致driver内存溢出
      *
      */
    //    scoreDF.join(studentDF.hint("broadcast"), $"s_id" === $"id")
    //      //.show()


    scoreDF.createOrReplaceTempView("score")

    studentDF.createOrReplaceTempView("student")


    /**
      * sql 中使用/*+ BROADCAST(a) */ 实现map join
      *
      */
    spark.sql(
      """
        |
        |select /*+ BROADCAST(a) */ * from student as a join score as b on a.id=b.s_id
        |
      """.stripMargin).show()


    while (true) {

    }


  }
}
