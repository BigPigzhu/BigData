import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD

object Demo1WordCount {
  def main(args: Array[String]): Unit = {

    /**
      * 1、创建spark环境
      *
      */

    val conf = new SparkConf()

    //指定spark程序名
    conf.setAppName("wc")

    //指定spark运行方式
    // local 本地测试运行

    //打包到集群运行的时候需要注释掉
    //默认单线程
    //[8] 指定线程数
    conf.setMaster("local")

    //sprk上下文对象，spark 入口
    val sc: SparkContext = new SparkContext(conf)


    /**
      * 2、读取文件
      *
      * RDD  ；相当于scala中的一个集合
      *
      */

    //    val lines: RDD[String] = sc.textFile("spark/data/words.txt")

    //将数据上传到hdfs
    //hadoop dfs -mkdir /data/words
    //hadoop dfs -put words.txt /data/words
    val lines: RDD[String] = sc.textFile("spark/data/words/", 5)



    // 2、将数据展开

    val words: RDD[String] = lines.flatMap(_.split(","))

    //3、将数据转换成keyvalue格式
    val kv: RDD[(String, Int)] = words.map(word => (word, 1))




    //4、统计单词的数量

    /**
      * reduceByKey : 通过ley对value进行聚合
      * 需要传入一个聚合函数
      *
      */

    val counts: RDD[(String, Int)] = kv.reduceByKey((x, y) => x + y, 3)


    //整理数据格式
    val result: RDD[String] = counts.map(kv => s"${kv._1},${kv._2}")


    //将数据保存到文件

    result.saveAsTextFile("spark/data/out5")


    /**
      * spark-submit --class Demo1WordCount --master yarn-client  spark-1.0.jar
      *
      */

  }
}
