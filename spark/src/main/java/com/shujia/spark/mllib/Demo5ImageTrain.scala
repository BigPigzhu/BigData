package com.shujia.spark.mllib

import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel}
import org.apache.spark.ml.linalg
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object Demo5ImageTrain {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[8]")
      .appName("mllib")
      .config("spark.sql.shuffle.partitions", 2)
      .getOrCreate()


    import spark.implicits._
    import org.apache.spark.sql.functions._

    //单独下去数据

    val data: DataFrame = spark.read
      .format("libsvm")
      .load("spark/data/image/train")

    data.printSchema()

    val newdata: DataFrame = data.as[(Double, linalg.Vector)]
      .map(kv => {
        val label: Double = kv._1

        val features: linalg.Vector = kv._2

        val array: Array[Double] = features.toArray.map(i => {
          if (i < 0) {
            1.0
          } else if (i > 1) {
            1.0
          } else {
            0.0
          }
        })

        (label, Vectors.dense(array))
      }).toDF("label", "features")


    //切分训练集和测试集
    val array: Array[Dataset[Row]] = newdata.randomSplit(Array(0.7, 0.3))
    //训练集
    val train: Dataset[Row] = array(0)

    //测试集
    val test: Dataset[Row] = array(1)


    //构建算法
    val logisticRegression: LogisticRegression = new LogisticRegression()



    //训练模型
    val model: LogisticRegressionModel = logisticRegression.fit(train)


    ///测试模型
    val frame: DataFrame = model.transform(test)


    val result: DataFrame = frame.select(sum(when($"label" === $"prediction", 1).otherwise(0)) / count($"label") as "p")

    result.show()

    model.save("spark/data/image/model")


  }
}
