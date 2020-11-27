package com.shujia.flink.core

import org.apache.flink.api.common.functions.{RichMapFunction, RuntimeContext}
import org.apache.flink.api.common.state.{ReducingState, ReducingStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions.ReduceFunction

object Demo5ReduceStateWC {

  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    val kvDS: DataStream[(String, Int)] = ds.flatMap(_.split(",")).map((_, 1))

    val countDS: DataStream[(String, Int)] = kvDS.keyBy(_._1).map(new ReduceMapFunaction)

    countDS.print()


    env.execute()


  }
}

class ReduceMapFunaction extends RichMapFunction[(String, Int), (String, Int)] {
  var reduceState: ReducingState[Long] = _

  override def open(parameters: Configuration): Unit = {
    val context: RuntimeContext = getRuntimeContext
    /**
      * 创建聚合状态
      * 保存单词的数量
      *
      */
    val reduceStateDesc: ReducingStateDescriptor[Long] = new ReducingStateDescriptor[Long]("reduce", new ReduceFunction[Long] {
      override def reduce(value1: Long, value2: Long): Long = value1 + value2
    }, createTypeInformation[Long])

    reduceState = context.getReducingState(reduceStateDesc)

  }

  override def map(value: (String, Int)): (String, Int) = {

    //更新状态
    //内存调用的时候上面自定义的聚合函数
    reduceState.add(value._2)

    //获取状态中保存的值
    val count: Long = reduceState.get()

    (value._1, count.toInt)

  }
}
