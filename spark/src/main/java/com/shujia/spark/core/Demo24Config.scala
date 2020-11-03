package com.shujia.spark.core

import org.apache.spark.SparkConf

object Demo24Config {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()

    conf.setMaster("lcoal")

    conf.setAppName("config")



    //在代码中设置spark运行参数
    conf.set("spark.shuffle.io.maxRetries", "10")
    conf.set("spark.shuffle.io.retryWait", "100s")


    /**
      *
      * 在铜鼓spark-submit 提交任务的时候也可以设置
      *
      *
      * spark-submit --conf spark.shuffle.io.maxRetries=10 --conf spark.shuffle.io.retryWait=100s
      *
      *
      *
      * 带吗中的优先级高于spark-submit
      *
      *
      */

  }

}
