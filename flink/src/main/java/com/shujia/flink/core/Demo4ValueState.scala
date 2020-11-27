package com.shujia.flink.core

import org.apache.flink.api.common.functions.{RichFlatMapFunction, RuntimeContext}
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

object Demo4ValueState {
  def main(args: Array[String]): Unit = {


    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    val kvDS: DataStream[(String, Int)] = ds.flatMap(_.split(",")).map((_, 1))


    /**
      * 在操作状态之前需要先进行keyBy
      *
      */
    val countDS: DataStream[(String, Int)] = kvDS.keyBy(_._1).flatMap(new WcFlatMapFunaction)


    countDS.print()


    env.execute()


  }
}

class WcFlatMapFunaction extends RichFlatMapFunction[(String, Int), (String, Int)] {

  /**
    *
    * vountState ： 保存单一值的一个状态
    *
    * 状态： 可以理解为内存中的一个变量， 在checkpoint的时候会将这个变量永久保存到hdfs中
    *
    */

  var vountState: ValueState[Long] = _

  override def open(parameters: Configuration): Unit = {
    /**
      * flink运行环境的对象
      * 可用使用这个对象，操作flink的时间，事件，状态
      *
      */
    val context: RuntimeContext = getRuntimeContext

    /**
      * 创建一个状态
      *
      */

    val valueStateDesc: ValueStateDescriptor[Long] = new ValueStateDescriptor[Long]("count", createTypeInformation[Long])

    /**
      * 由于前面做了keyBy ，所以每一个key都会有一个状态的对象
      *
      * 在状态中保存单词的数量
      */
    vountState = context.getState(valueStateDesc)


  }

  override def flatMap(value: (String, Int), out: Collector[(String, Int)]): Unit = {

    //获取之前key的状态 中的值
    val last: Long = vountState.value()


    //通过当前的值加上之前的值，更新状态
    vountState.update(value._2 + last)

    //将最新单词的数量发送到下游

    out.collect((value._1, (value._2 + last).toInt))
  }
}
