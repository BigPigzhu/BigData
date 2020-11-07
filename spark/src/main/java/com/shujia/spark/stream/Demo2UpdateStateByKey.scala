package com.shujia.spark.stream

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Demo2UpdateStateByKey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
      .setMaster("local[2]") // spark streaming 需要指定多个资源,  结束数据会占用一个
      .setAppName("wc")

    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Durations.seconds(5))
    //设置checkpoint路径
    ssc.checkpoint("spark/data/checkpoint")

    val ds: ReceiverInputDStream[String] = ssc.socketTextStream("master", 9999)


    /**
      *
      * 有状态算子
      *
      * 将每一个统计单词的数量看着是一个状态(数值)
      * 后面的计算不停的去更新之前的状态
      *
      */

    val kvRDD: DStream[(String, Int)] = ds.flatMap(_.split(","))
      .map((_, 1))


    /**
      *
      * @param seq    当前batch 可以的所有的value
      * @param option 之前一个key计算的状态,  之前的结果需要保存的checkpoint中
      * @return 返回一个新的状态
      */
    def updateFunc(seq: Seq[Int], option: Option[Int]): Option[Int] = {
      //用当前batch 更新之前的状态,返回一个新的状态

      //1 统计当前batch的单词的数量
      val currCount: Int = seq.sum

      //2  获取之前统计的结果
      val lastCount: Int = option.getOrElse(0)

      //当前的单词的数量加上之前单词的数量得到新的数量
      Some(currCount + lastCount)
    }


    //有状态算子, 每个5秒更新之前计算的状态
    val countDS: DStream[(String, Int)] = kvRDD.updateStateByKey(updateFunc)

    //打印
    countDS.print()


    ssc.start()
    ssc.awaitTermination()


  }
}
