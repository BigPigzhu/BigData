package com.shujia.spark.sql

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{Column, DataFrame, SparkSession}

object Demo9Burk {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("brk")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._


    val burk: DataFrame = spark.read
      .format("csv")
      .option("sep", ",")
      .schema("burk STRING , year STRING , tsl01 DOUBLE ,tsl02 DOUBLE ,tsl03 DOUBLE ,tsl04 DOUBLE ,tsl05 DOUBLE ,tsl06 DOUBLE ,tsl07 DOUBLE ,tsl08 DOUBLE ,tsl09 DOUBLE ,tsl10 DOUBLE ,tsl11 DOUBLE ,tsl12 DOUBLE ")
      .load("spark/data/burks.txt")


    //对多次使用df进行缓存
    burk.cache()


    //行数
    //    burk.show(1000)

    //完全显示一行数据
    //    burk.show(truncate = true)


    /**
      *
      * 1、统计每个公司每年按月累计收入
      *
      * 输出结果
      * 公司代码,年度,月份,当月收入,累计收入
      *
      */

    burk.createOrReplaceTempView("burk")


    /**
      * sql
      *
      */


    spark.sql(
      """
        |
        |select burk,year,month,pic, sum(pic)  over(partition by burk,year  order by month) as sum_pic from
        |(
        |select burk,year,month,pic from burk
        |LATERAL VIEW explode(map('01',tsl01,'02',tsl02,'03',tsl03,'04',tsl04,'05',tsl05,'06',tsl06,'07',tsl07,'08',tsl08,'09',tsl09,'10',tsl10,'11',tsl11,'12',tsl12)) t  as month,pic
        |)
        |
      """.stripMargin).show(1000)


    /**
      * DSL
      *
      */

    val map1: Column = map(
      expr("01"), $"tsl01",
      expr("02"), $"tsl02",
      expr("03"), $"tsl03",
      expr("04"), $"tsl04",
      expr("05"), $"tsl05",
      expr("06"), $"tsl06",
      expr("07"), $"tsl07",
      expr("08"), $"tsl08",
      expr("09"), $"tsl09",
      expr("10"), $"tsl10",
      expr("11"), $"tsl11",
      expr("12"), $"tsl12"
    )


    burk
      .select($"burk", $"year", explode(map1) as Array("month", "pic")) //列转行
      .select($"burk", $"year", $"month", $"pic", sum($"pic") over Window.partitionBy($"burk", $"year").orderBy($"month") as "sum_pic")
      .show()


    /**
      *
      * 2、统计每个公司当月比上年同期增长率
      * 公司代码,年度,月度,增长率（当月收入/上年当月收入 - 1）
      *
      */

    burk
      .select($"burk", $"year", explode(map1) as Array("month", "pic")) //列转行
      .select($"burk", $"year", $"month", $"pic", lag($"pic", 1, 0.0) over Window.partitionBy($"burk", $"month").orderBy($"year") as "last_pic")
      .select($"burk", $"year", $"month", $"pic", $"pic" / $"last_pic" - 1 as "f")
      .select($"burk", $"year", $"month", $"pic", round(coalesce($"f", expr("0.0")), 6) as "f")
      .show()

  }
}
