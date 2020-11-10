package com.shujia.spark.mllib

import org.apache.spark.ml.classification.LogisticRegressionModel
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo6Modeluse {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[8]")
      .appName("mllib")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()


    import spark.implicits._
    import org.apache.spark.sql.functions._

    //加载模型
    val model: LogisticRegressionModel = LogisticRegressionModel.load("spark/data/image/model")


    //读取图片
    val df: DataFrame = spark.read
      .format("image")
      .load("spark/data/image/10.jpg")


    val test: DataFrame = df.select($"image.data")
      .as[Array[Byte]]
      .map(data => {
        val features: Array[Double] = data.map(byte => {
          val int: Int = byte.toInt
          if (int == 0) {
            0.0
          } else {
            1.0
          }
        })
        (1, Vectors.dense(features))
      }).toDF("label", "features")


    //预测
    val frame: DataFrame = model.transform(test)
    frame.show(false)


  }
}
