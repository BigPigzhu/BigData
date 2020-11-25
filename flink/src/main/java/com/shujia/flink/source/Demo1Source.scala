package com.shujia.flink.source

import org.apache.flink.streaming.api.scala._

object Demo1Source {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment


    /**
      *
      * 1 基于本地集合构建DS
      *
      */

    //有界流
    val ds1: DataStream[Int] = env.fromCollection(List(1, 2, 3, 4, 5, 6, 7))

    ds1.print()

    /**
      *
      * 读取文件构建DS
      */

    //有界流
    val ds2: DataStream[String] = env.readTextFile("flink/data/words.txt")

    ds2.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      .sum(1)
      .print()


    /**
      * 3、 读取socket创建ds
      *
      */

    //无界流
    val ds3: DataStream[String] = env.socketTextStream("master", 8888)

    //    ds3.print()


    /**
      * 4\ 使用自定义source
      *
      */

    val ds4: DataStream[Int] = env.addSource(new Demo2SourceFunaction())


    ds4.print()


    env.execute()


  }

}
