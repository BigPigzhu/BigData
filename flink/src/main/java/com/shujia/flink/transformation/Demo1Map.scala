package com.shujia.flink.transformation

import org.apache.flink.api.common.functions.{MapFunction, RichMapFunction}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._

object Demo1Map {

  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val ds: DataStream[String] = env.socketTextStream("master", 8888)


    val ds1: DataStream[Int] = ds.map(new RichMapFunction[String, Int] {

      override def open(parameters: Configuration): Unit = {

        //可以在这个代开外部数据库的连接
        //然后再map方法中查询数据

        //open ： 每一个分区中只会运行一次

        //可以用来实现mapjoin
      }

      override def close(): Unit = {

      }

      override def map(value: String): Int = {
        value.length
      }
    })

    val ds3: DataStream[Int] = ds.map(_.length)

    ds1.print()

    env.execute()

  }

}
