package com.shujia.spark.mllib

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.ml.clustering.{KMeans, KMeansModel}
import org.apache.spark.ml.linalg
import org.apache.spark.ml.linalg.Vectors

object Demo3Kmeans {
  def main(args: Array[String]): Unit = {
    /**
      * kmeans : 据类 (无监督机器学习)
      *
      */

    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("mllib")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()


    import spark.implicits._
    import org.apache.spark.sql.functions._


    val data: Dataset[Array[Double]] = spark.read
      .format("csv")
      .load("spark/data/kmeans.txt")
      .as[String]
      .map(line => {
        val doubles: Array[Double] = line.split(" ").map(_.toDouble)
        doubles
      })


    /**
      * 故偶见算法
      *
      */
    val kMeans: KMeans = new KMeans()
      .setK(2) // 类的数量
      .setFeaturesCol("value") // 数据列


    //训练模型
    val model: KMeansModel = kMeans.fit(data)


    //据类
    val frame: DataFrame = model.transform(data)


    //获取结果
    frame.show()


  }

}
