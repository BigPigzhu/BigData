package com.shujia.flink.transformation

import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions.{FlatMapFunction, RichFlatMapFunction}
import org.apache.flink.configuration.Configuration
import org.apache.flink.util.Collector

object Demo2FlatMap {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val ds: DataStream[String] = env.socketTextStream("master", 8888)


    //java中的写法
    val ds1: DataStream[String] = ds.flatMap(new RichFlatMapFunction[String, String] {

      override def open(parameters: Configuration): Unit = {
        //可以在这个代开外部数据库的连接
        //然后再map方法中查询数据

        //open ： 每一个分区中只会运行一次

        //可以用来实现mapjoin
      }

      override def close(): Unit = {

      }

      override def flatMap(value: String, out: Collector[String]): Unit = {
        value.split(",").foreach(word => {
          //将数据发送到下游
          out.collect(word)
        })

        println("flatMap")
      }
    })

    //scala中的写法
    ds.flatMap(_.split(","))

    ds1.print()

    //促发任务执行
    env.execute()

  }

}
