package com.shujia.spark.sql

import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo4DataFrameAPI {
  def main(args: Array[String]): Unit = {


    val spark: SparkSession = SparkSession
      .builder()
      .config("spark.sql.shuffle.partitions", 1) // 设sql shuffle之后分区数
      .appName("df")
      .master("local")
      .getOrCreate()

    //大投入隐式转换
    import spark.implicits._

    val df: DataFrame = spark
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
      * .show()  相当于action算子
      *
      */

    /**
      * select
      *
      */

    df.select("name", "age").show() // 通过列名选择
    df.select(df("age"), df("name")).show() // 通过列选择

    //在选择的时候对数据进行处理,  支持sql中大部分写法
    df.select(df("name"), df("age").+(1)).show()
    df.select(df("name"), df("age") + 1).show()

    //通过$ 简写
    //as 取别名
    df.select($"name", $"age" - 1 as "age").show()


    /**
      * where
      *
      */

    df.where("age> 21").show() // 传入一个字符串表达式

    df.where($"age" <= 22).show() //使用列的方式


    /**
      *
      * group by
      *
      * 分组之后必须接一个聚合函数
      *
      */

    //导入spark sql 常用函数
    import org.apache.spark.sql.functions._

    df.groupBy($"clazz")
      .agg(count($"clazz") as "num")
      .show()

    df.groupBy($"clazz")
      .agg(sum($"age") as "sumage")
      .show()


    df.groupBy($"clazz")
      .agg(avg($"age"))
      .show()

    df.groupBy($"clazz")
      .agg(max($"age"))
      .show()


    /**
      * join
      *
      */

    df.join(scoreDF, $"id" === $"s_id")
      .select("id", "name", "age", "gender", "clazz", "sco")
      .groupBy("id", "name")
      .agg(sum($"sco") as "sumSco")
      .select("id", "name", "sumSco")
      .show()

    /**
      * sql  执行流程
      *
      * from  -->  where --> join --> on --> group by --> having --> select  ->> order by -->  limit
      *
      *
      */


  }
}
