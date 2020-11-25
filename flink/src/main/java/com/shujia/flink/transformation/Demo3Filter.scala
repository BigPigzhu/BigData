package com.shujia.flink.transformation

import org.apache.flink.api.common.functions.{FilterFunction, RichFilterFunction}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._

object Demo3Filter {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    ds.filter(_.equals("java"))

    val ds1: DataStream[String] = ds.filter(new RichFilterFunction[String] {

      override def open(parameters: Configuration): Unit = {

        //连接hbase
        //再filter中去重
      }

      override def close(): Unit = {

      }


      override def filter(value: String): Boolean = {
        value.equals("java")
      }
    })

    ds1.print()

    env.execute()

  }

}
