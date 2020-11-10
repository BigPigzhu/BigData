package com.shujia.spark.mllib

import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel, NaiveBayes, NaiveBayesModel}
import org.apache.spark.ml.feature.{HashingTF, IDF, IDFModel, Tokenizer}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object Demo8TextClaster {
  def main(args: Array[String]): Unit = {

    /**
      * 文本分类
      *
      */


    val spark: SparkSession = SparkSession.builder()
      .master("local[8]")
      .appName("mllib")
      .config("spark.sql.shuffle.partitions", 8)
      .getOrCreate()


    import spark.implicits._
    import org.apache.spark.sql.functions._


    val data: DataFrame = spark.read
      .format("csv")
      .option("sep", "\t")
      .schema("label double, text string")
      .load("spark/data/train.txt")
      .repartition(8)

    //s使用ik分词器对数据进行分词
    val wordsData: DataFrame = data.as[(Double, String)]
      .map(kv => {
        //分词
        (kv._1, Demo7IK.fit(kv._2))
      })
      .filter(_._2.nonEmpty) //去掉空数据
      .map(kv => (kv._1, kv._2.mkString(" "))) //按空格拼接数据
      .toDF("label", "text")


    /**
      * 将数据转换成向量，加上tf-idf
      */

    //英文分词器，默认安装空格分割
    val tokenizer: Tokenizer = new Tokenizer()
      .setInputCol("text")
      .setOutputCol("words")

    val tokenizerDF: DataFrame = tokenizer.transform(wordsData)


    // 加上tf， 转换成一个稀疏向量
    val hashingTF: HashingTF = new HashingTF()
      .setInputCol("words")
      .setOutputCol("rawFeatures")

    val hashingTFDF: DataFrame = hashingTF.transform(tokenizerDF)


    /**
      * 增加idf
      *
      */

    val idf: IDF = new IDF()
      .setInputCol("rawFeatures")
      .setOutputCol("features")

    //训练idf模型
    val idfModel: IDFModel = idf.fit(hashingTFDF)

    //计算idf
    val idfDF: DataFrame = idfModel.transform(hashingTFDF)


    //将数据切分成训练集和测试集
    val array: Array[Dataset[Row]] = idfDF.randomSplit(Array(0.7, 0.3))
    val train: Dataset[Row] = array(0)
    val test: Dataset[Row] = array(1)


    /**
      * 贝叶斯分类
      * 适用于文本分类（垃圾邮件分类）
      *
      */

    val naiveBayes: NaiveBayes = new NaiveBayes()

    //将数据带入算法训练模型
    val model: NaiveBayesModel = naiveBayes.fit(train)

    //    val logisticRegression: LogisticRegression = new LogisticRegression()
    //    val model: LogisticRegressionModel = logisticRegression.fit(train)

    //模型评估
    val frame: DataFrame = model.transform(test)

    frame.cache()

    frame.show(1000, false)

    //计算准确率
    val result: DataFrame = frame.select(sum(when($"label" === $"prediction", 1).otherwise(0)) / count($"label") as "p")

    result.show()


    //保存模型
    idfModel.save("spark/data/text/idfmodel")
    model.save("spark/data/text/model")


  }
}
