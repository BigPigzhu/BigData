package com.shujia.spark.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo3CreateDataFrame {
  def main(args: Array[String]): Unit = {

    /**
      * 创建df
      *
      * 1 通过rdd床架df
      * 2 读取json格式的数据
      * 3 读取csv合适的数据    常用
      * 4 读取jdbc创建df
      *
      *
      */

    val spark: SparkSession = SparkSession
      .builder()
      .appName("df")
      .master("local")
      .getOrCreate()

    //大投入隐式转换
    import spark.implicits._


    //1 通过rdd床架df

    //创建rdd
    val rdd: RDD[String] = spark
      .sparkContext // 获取sparkContext
      .textFile("spark/data/students.txt")


    //将rdd的类型转换成一个自定义类的对象
    val studentRDD: RDD[Student] = rdd.map(line => {
      val split: Array[String] = line.split(",")

      Student(split(0), split(1), split(2).toInt, split(3), split(4))
    })

    //由于自定义类由列名和类的类型,所以可以直接转换成一个df


    val df: DataFrame = studentRDD.toDF()

    df.show()

    //==========================================================

    println("=" * 100)

    //也可以使用元组
    val tupleRDD = rdd.map(line => {
      val split: Array[String] = line.split(",")

      (split(0), split(1), split(2).toInt, split(3), split(4))
    })


    //转换成df 指定列名
    val df1: DataFrame = tupleRDD.toDF("id", "name", "age", "gender", "clazz")

    df1.show()


    println("=" * 100)

    //2 读取json格式的数据

    val jsonDF: DataFrame = spark
      .read
      .json("spark/data/students.json")

    jsonDF.show()

    //==========================================================

    //3 读取csv合适的数据

    val csvDF: DataFrame = spark
      .read
      .format("csv") //指定数据格式
      .option("sep", ",") // 指定数据分割方式,默认是逗号
      // .option("header", "true") //使用数据第一行作用列名
      //.option("inferSchema", "true") // 类型自动推断

      //指定数据列名和列的类型
      .schema("id STRING , name STRING , age INT , gender STRING , clazz STRING")
      .load("spark/data/students.txt") //加载数据


    //打印表结构
    csvDF.printSchema()

    csvDF.show()

    //==========================================================

    //4 读取jdbc创建df


    val jdbcDF: DataFrame = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://master:3306?useUnicode=true&characterEncoding=UTF-8")
      .option("dbtable", "student.user")
      .option("user", "root")
      .option("password", "123456")
      .load()

    jdbcDF.show()


  }

  case class Student(id: String, name: String, age: Int, gender: String, clazz: String)

}
