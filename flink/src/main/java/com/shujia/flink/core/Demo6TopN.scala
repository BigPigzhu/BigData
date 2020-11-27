package com.shujia.flink.core

import java.lang

import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimerService
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object Demo6TopN {
  def main(args: Array[String]): Unit = {

    /**
      *
      * 统计最新15秒最热门的前两个商品，每隔5秒统计一次
      *
      */

    /*
itemid,c
001,1
001,1
001,1
002,1
002,1
003,1
003,1
003,1
003,1
001,1
001,1
001,1
002,1
003,1
001,1
002,1
003,1
 */

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    val kvDS: DataStream[(String, Int)] = ds.map(_.split(",")).map(split => (split(0), 1))

    //统计窗口中商品的数量，返回的数据带上了窗口的结束时间
    val windowCountDS: DataStream[(String, Int, Long)] = kvDS.keyBy(_._1)
      .timeWindow(Time.seconds(15), Time.seconds(5))
      .process(new CountProcessWindowFunction)


    //统计每一个窗口中前两个商品
    val topNDS: DataStream[(String, Int, Long)] = windowCountDS
      .keyBy(_._3) //按照窗口的结束时间keyby
      .process(new TopNKeyedProcessFunction)

    topNDS.print()


    env.execute()


  }
}

/**
  * ProcessWindowFunction ： 底层api,  可以获取窗口开始和结束事件
  *
  */
class CountProcessWindowFunction extends ProcessWindowFunction[(String, Int), (String, Int, Long), String, TimeWindow] {

  /**
    *
    *
    * @param key      一个商品
    * @param context  院校环境，可以获取窗口信息
    * @param elements ： 用一个key在在一个窗口中所有的数据
    * @param out      ： 将数据发送到下游的一个对象
    */
  override def process(key: String, context: Context, elements: Iterable[(String, Int)], out: Collector[(String, Int, Long)]): Unit = {

    //获取窗口信息
    val window: TimeWindow = context.window
    //窗口的结束事件
    val endTime: Long = window.getEnd

    //统计商品 的数量
    val count: Int = elements.map(_._2).sum

    out.collect(key, count, endTime)
  }
}


class TopNKeyedProcessFunction extends KeyedProcessFunction[Long, (String, Int, Long), (String, Int, Long)] {

  var listState: ListState[(String, Int, Long)] = _

  override def open(parameters: Configuration): Unit = {
    val context: RuntimeContext = getRuntimeContext

    /**
      * ListState : 每一个key都会哟一个，保存多条数据
      *
      */
    val listStateDesc: ListStateDescriptor[(String, Int, Long)] = new ListStateDescriptor[(String, Int, Long)]("valueList", createTypeInformation[(String, Int, Long)])

    listState = context.getListState(listStateDesc)
  }

  /**
    * 每一条数据都会执行一次，对于同一个key而言
    *
    * @param value ： 数据
    * @param ctx   ：环境
    * @param out   ： 输出的对象
    */
  override def processElement(value: (String, Int, Long), ctx: KeyedProcessFunction[Long, (String, Int, Long), (String, Int, Long)]#Context, out: Collector[(String, Int, Long)]): Unit = {

    //将同一个窗口中的数据保存到状态中
    listState.add(value)


    //定时器服务
    val timerService: TimerService = ctx.timerService()

    /**
      * 注册定时器： 当事件到达定时器的事件后会触发onTimer 执行
      *
      */
    timerService.registerProcessingTimeTimer(value._3 + 1000)

  }

  /**
    *
    * 需要创建一个定时器。定时器事件到了就会触发
    *
    * @param timestamp 当前时间戳
    * @param ctx       ： 运行环境
    * @param out       ; 输出数据的对象
    */
  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Long, (String, Int, Long), (String, Int, Long)]#OnTimerContext, out: Collector[(String, Int, Long)]): Unit = {

    //统计同一个窗口中前两个商品
    val values: lang.Iterable[(String, Int, Long)] = listState.get()

    import scala.collection.JavaConversions._

    val list: List[(String, Int, Long)] = values.toList


    //排序取TopN
    val topN: List[(String, Int, Long)] = list.sortBy(-_._2).take(2)


    //将数据发送到下游
    topN.foreach(value => out.collect(value))


    //回收状态
    listState.clear()
  }
}

