package com.shujia.spark.mllib

import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object Demo2Person {
  def main(args: Array[String]): Unit = {

    /**
      * 机器学习过程
      * 1    特征工程
      * 2    将数据切分成训练集和测试集
      * 3    将训练集带入算法,训练模型  ( spark 迭代计算 )
      * 4    模型评估,  是用测试集评估模型
      * 5    保存模型
      * 5    模型使用
      *
      */


    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("mllib")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()


    import spark.implicits._
    import org.apache.spark.sql.functions._

    /**
      * 读取SVm数据
      *
      */

    val data: DataFrame = spark
      .read
      .format("libsvm")
      .load("spark/data/人体指标.txt")


    // 切分训练集和测试集

    val array: Array[Dataset[Row]] = data.randomSplit(Array(0.7, 0.3))

    //训练集
    val train: Dataset[Row] = array(0)

    //测试集
    val test: Dataset[Row] = array(1)

    /**
      * 选择算法
      *
      */

    //逻辑回归
    val logisticRegression: LogisticRegression = new LogisticRegression()


    /**
      * 训练模型
      *
      * spark 迭代计算,收敛
      *
      */

    val model: LogisticRegressionModel = logisticRegression.fit(train)


    /**
      * 模型评估
      *
      */

    val frame: DataFrame = model.transform(test)


    /**
      * 计算准确率
      *
      * 正确的数量  /  总数
      *
      *
      * prediction ; 预测列
      *
      */


    val result: DataFrame = frame.select(sum(when($"label" === $"prediction", 1).otherwise(0)) / count($"label") as "p")

    result.show()


    /**
      * 保存模型
      *
      */

    model.save("spark/data/model")


    /**
      * 在其他地方加载模型,使用模型
      *
      */

    //val model1: LogisticRegressionModel = LogisticRegressionModel.load("spark/data/model")
  }
}
