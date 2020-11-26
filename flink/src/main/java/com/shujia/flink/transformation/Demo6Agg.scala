package com.shujia.flink.transformation

import org.apache.flink.streaming.api.scala._

object Demo6Agg {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(4)

    val linesDS: DataStream[String] = env.readTextFile("flink/data/students.txt")

    val studentDS: DataStream[Student] = linesDS.map(line => {
      val split: Array[String] = line.split(",")
      Student(split(0), split(1), split(2).toInt, split(3), split(4))
    })

    /**
      *
      * max ：返回流中最大的值， 其他的列可能不对
      * maxBy : 返回流中最大值的键   其他的列还可以用
      *
      *
      * max , maxBy  min minBy  sum
      *
      */

    studentDS
      .keyBy(_.clazz)
      .maxBy("age")
      .print()

    env.execute()


  }

  case class Student(id: String, name: String, age: Int, gender: String, clazz: String)

}
