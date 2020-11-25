package com.shujia.flink.core

import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode

object Demo1batchWC {
  def main(args: Array[String]): Unit = {

    /**
      * 创建flink环境
      *
      */

    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment


    //设置并行度
    env.setParallelism(1)

    //读取数据
    val linesDS: DataSet[String] = env.readTextFile("flink/data/words.txt")

    val wordsDS: DataSet[String] = linesDS.flatMap(_.split(","))

    val kvDS: DataSet[(String, Int)] = wordsDS.map((_, 1))

    //统计单词的数量
    val countDS: AggregateDataSet[(String, Int)] = kvDS.groupBy(0).sum(1)


    //countDS.print()

    //保存数据
    countDS.writeAsText("flink/data/out1", WriteMode.OVERWRITE)


    //触发flink任务执行
    env.execute("wc")

  }
}
