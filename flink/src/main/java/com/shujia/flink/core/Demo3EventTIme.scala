package com.shujia.flink.core

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object Demo3EventTIme {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    //将并行度设置为1 ,不然看不到效果
    env.setParallelism(1)

    /*
001,zhsngsan,1599810953000
001,zhsngsan,1599810954000
001,zhsngsan,1599810955000
001,zhsngsan,1599810957000
001,zhsngsan,1599810956000
001,zhsngsan,1599810961000
001,zhsngsan,1599810965000
001,zhsngsan,1599810966000
001,zhsngsan,1599810966000
001,zhsngsan,1599810966000
001,zhsngsan,1599810966000
001,zhsngsan,1599810966000
001,zhsngsan,1599810970000
002,lisi,1599810955000
  */


    /**
      * flink默认时间模式式处理时间
      *
      */

    //设置时间模式为处理时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)


    val ds: DataStream[String] = env.socketTextStream("master", 8888)

    val eventsDS: DataStream[Event] = ds.map(line => {
      val split: Array[String] = line.split(",")
      Event(split(0), split(1), split(2).toLong)
    })

    //指定时间字段
    //水位线默认等于时间戳最大的一条数据的时间戳
    //val eventTimeDS: DataStream[Event] = eventsDS.assignAscendingTimestamps(_.ts)

    //指定时间字段和最大允许数据延迟到达的时间
    val eventTimeDS: DataStream[Event] = eventsDS.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[Event](Time.seconds(5)) {
      override def extractTimestamp(element: Event): Long = {
        //指定时间时间
        element.ts
      }
    })

    eventTimeDS
      .map(event => (event.id, 1))
      .keyBy(_._1)
      .timeWindow(Time.seconds(5))
      .sum(1)
      .print()


    env.execute()


  }

  case class Event(id: String, name: String, ts: Long)

}
