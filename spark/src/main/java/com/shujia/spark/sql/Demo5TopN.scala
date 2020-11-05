package com.shujia.spark.sql

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql._

object Demo5TopN {
  def main(args: Array[String]): Unit = {

    /**
      * 统计每个省市人流量最多的前两个区县
      *
      */

    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("topn")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()

    val df: DataFrame = spark.read
      .format("csv")
      .option("sep", ",")
      .schema("mdn STRING ,grid STRING , city STRING ,county STRING , time STRING , start_date STRING , end_date STRING , day STRING")
      .load("spark/data/dianxin_data")

    /**
      * sql
      *
      */
    df.createOrReplaceTempView("dianxin")


    val resultRF: DataFrame = spark.sql(
      """
        |
        |select * from
        |(
        |select city,county,c, row_number() over(partition by city order by c desc) rank from
        |(
        |select city , county , count(distinct mdn) c from dianxin group by city , county
        |) as a
        |) as b
        |where rank <= 2
        |
      """.stripMargin)

    resultRF.show(1000000)

    /**
      *
      * DSL
      */

    import spark.implicits._
    import org.apache.spark.sql.functions._

    val rdd2: Dataset[Row] = df.groupBy($"city", $"county")
      .agg(countDistinct($"mdn") as "c")
      .select($"city", $"county", $"c", row_number() over Window.partitionBy($"city").orderBy($"c".desc) as "rank")
      .where($"rank" <= 2)



    //保存数据
    rdd2.write
      .mode(SaveMode.Overwrite)
      .csv("spark/data/out7")


  }
}
