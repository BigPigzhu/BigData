package com.shujia.flink.transformation

import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

object Demo9SideOut {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(4)

    val linesDS: DataStream[String] = env.readTextFile("flink/data/students.txt")

    val man: OutputTag[String] = OutputTag[String]("男")
    val nv: OutputTag[String] = OutputTag[String]("女")

    /**
      * ProcessFunction  ； 底层的api可以操作时间，事件， 状态
      *
      */

    val resultDS: DataStream[String] = linesDS.process(new ProcessFunction[String, String] {
      override def processElement(value: String, ctx: ProcessFunction[String, String]#Context, out: Collector[String]): Unit = {

        //将数据发送到下游
        out.collect("test")

        value.split(",")(3) match {
          case "男" => ctx.output(man, value) //Side Outputs
          case "女" => ctx.output(nv, value) //Side Outputs
        }
      }
    })

    //获取side output 输出的iu
    val maxDS: DataStream[String] = resultDS.getSideOutput(man)
    val nvDS: DataStream[String] = resultDS.getSideOutput(nv)


    nvDS.print()

    env.execute()


  }
}
