package com.shujia.flink.source

import org.apache.flink.streaming.api.functions.source.{ParallelSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.functions.source

object Demo6Parallel {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(2)

    val ds: DataStream[Int] = env.addSource(new Demo6ParallelSourceFunaction)

    ds.print()

    env.execute()
  }
}

/**
  * SourceFunction 单一的source
  * ParallelSourceFunction : 并行的source, 会启动多个task读取数据
  * RichSourceFunction ： 在SourceFunction的基础上多个open和close方法
  * RichParallelSourceFunction ;
  */

class Demo6ParallelSourceFunaction extends ParallelSourceFunction[Int] {
  override def run(ctx: SourceFunction.SourceContext[Int]): Unit = {

    val l: List[Int] = List(1, 2, 3, 4, 5)

    l.foreach(i => {

      //发送数据到下游
      ctx.collect(i)
    })

  }

  override def cancel(): Unit = {

  }
}
