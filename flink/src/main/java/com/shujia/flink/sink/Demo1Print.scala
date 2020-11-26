package com.shujia.flink.sink

import java.io.PrintStream

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala._

object Demo1Print {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    //使用自定义sink
    ds.addSink(new TestPrintSinkFunaction)

    env.execute()
  }

}

/**
  * 自定义sink  继承RichSinkFunction 重写invoke 方法
  *
  *
  * open 和close 方法每隔分区中只会执行一次
  * invoke ： 每一条数据执行一尺
  */

class TestPrintSinkFunaction extends RichSinkFunction[String] {
  var out: PrintStream = _

  override def open(parameters: Configuration): Unit = {
    //初始化连接

    out = System.out
  }

  override def invoke(value: String, context: SinkFunction.Context[_]): Unit = {
    out.println("自定义：" + value)
  }
}

