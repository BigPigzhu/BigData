package com.shujia.spark.mllib

import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.ml.linalg
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.{DataFrame, SparkSession}

object Demo1Vector {

  def main(args: Array[String]): Unit = {

    /**
      * 稠密向量
      * 主要用于保存数据特征 (x)
      *
      */


    //代表一行数据的特征
    val vector: linalg.Vector = Vectors.dense(Array(0.0, 0.1, 0.2, 0.0, 0.0, 0.2, 0.0, 0.0, 0.0, 0.2, 0.0))

    println(vector)

    /**
      * 稀疏向量
      *
      * 如果数据中0 比较多  ,可以节省空间
      *
      */

    val vector1: linalg.Vector = Vectors.sparse(11, Array(1, 2, 5, 9), Array(0.1, 0.2, 0.2, 0.2))

    println(vector1)

    //相互转换
    println(vector1.toDense)


    /**
      * LabeledPoint ; 代表一条训练集
      * 由 y  和多个x  组成
      *
      *
      */
    val pos: LabeledPoint = LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0))


    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("mllib")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()


    /**
      * 读取SVm数据
      *
      */

    val data: DataFrame = spark
      .read
      .format("libsvm")
      .load("spark/data/人体指标.txt")


    data.show(false)

  }

}
