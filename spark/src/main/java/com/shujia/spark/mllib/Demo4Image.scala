package com.shujia.spark.mllib

import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Demo4Image {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[8]")
      .appName("mllib")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()


    import spark.implicits._
    import org.apache.spark.sql.functions._

    /**
      *
      * 特征工程
      *
      */


    /**
      *
      * root
      * |-- image: struct (nullable = true)
      * |    |-- origin: string (nullable = true)  文件名
      * |    |-- height: integer (nullable = true) 高度
      * |    |-- width: integer (nullable = true)  宽度
      * |    |-- nChannels: integer (nullable = true)
      * |    |-- mode: integer (nullable = true)
      * |    |-- data: binary (nullable = true)  数据
      *
      */

    val df: DataFrame = spark.read
      .format("image")
      .load("D:\\课件\\机器学习数据\\手写数字\\train")
      .coalesce(8) //减少分区

    df.printSchema()

    val feas: DataFrame = df.select($"image.origin", $"image.data")
      .as[(String, Array[Byte])]
      .map(kv => {
        val name: String = kv._1.split("/").last
        val array: Array[Double] = kv._2.map(_.toDouble)

        (name, Vectors.dense(array))
      })
      .toDF("name", "features")


    /**
      * 读取图片的标签
      *
      */

    val labelDF: DataFrame = spark.read
      .format("csv")
      .option("sep", " ")
      .schema("name string , label double")
      .load("D:\\课件\\机器学习数据\\手写数字\\train.txt")

    //管理标签
    val train: DataFrame = feas
      .join(labelDF.hint("broadcast"), "name")
      .select("label", "features")

    //保存数据
    train
      .write
      .mode(SaveMode.Overwrite)
      .format("libsvm")
      .save("spark/data/image/train")


  }

}
