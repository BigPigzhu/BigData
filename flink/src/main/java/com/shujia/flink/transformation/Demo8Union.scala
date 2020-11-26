package com.shujia.flink.transformation

import org.apache.flink.streaming.api.scala._

object Demo8Union {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val ds1: DataStream[Int] = env.fromCollection(List(1, 2, 3, 4))
    val ds2: DataStream[Int] = env.fromCollection(List(5, 6, 7, 8))


    val ds3: DataStream[Int] = ds1.union(ds2)


    ds3.print()



    env.execute()


  }

}
