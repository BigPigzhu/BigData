package com.shujia.spark.sql

import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo10Stu {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("brk")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._


    val stu: DataFrame = spark.read
      .format("csv")
      .option("sep", ",")
      .schema("name STRING , cou STRING , score DOUBLE")
      .load("spark/data/stu.txt")

    /**
      *
      * 1、行列转换
      *
      * 表1
      * 姓名,科目,分数
      * name,item,score
      * 张三,数学,33
      * 张三,英语,77
      * 李四,数学,66
      * 李四,英语,78
      *
      *
      * 表2
      * 姓名,数学,英语
      * name,math,english
      * 张三,33,77
      * 李四,66,78
      *
      * 1、将表1转化成表2
      * 2、将表2转化成表1
      *
      *
      */

    stu.createOrReplaceTempView("student")


    spark.sql(
      """
        |
        |select
        |name,
        | sum(case when cou='数学' then score else 0 end) as a,
        | sum(case when cou='英语' then score else 0 end) as b
        | from
        |student group by name
        |
        |
      """.stripMargin) //.show()


    spark.sql(
      """
        |
        |select name, `数学` as a , `英语` as b  from
        |student
        |pivot(sum(score) for cou in ('数学','英语'))
        |
        |
      """.stripMargin).show()


    //行转列
    val result: DataFrame = stu
      .groupBy($"name")
      .pivot($"cou", List("数学", "英语")) //  透视
      .agg(sum($"score"))


    result.show()


    //列传行
    result.select()
      .select($"name", explode(map(expr("`数学`"), $"数学", expr("`英语`"), $"英语")) as Array("cou", "score"))
      .show()

  }

}
