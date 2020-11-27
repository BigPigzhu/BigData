package com.shujia.flink.core

import org.apache.flink.contrib.streaming.state.RocksDBStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.api.scala._
import org.apache.flink.runtime.state.StateBackend

object Demo7CheckPoint {
  def main(args: Array[String]): Unit = {


    /**
      * 创建流处理的环境
      *
      */
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment



    // 每 1000ms 开始一次 checkpoint
    env.enableCheckpointing(1000)

    // 高级选项：

    // 设置模式为精确一次 (这是默认值)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)

    // 确认 checkpoints 之间的时间会进行 500 ms
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500)

    // Checkpoint 必须在一分钟内完成，否则就会被抛弃
    env.getCheckpointConfig.setCheckpointTimeout(60000)

    // 开启在 job 中止后仍然保留的 externalized checkpoints
    env.getCheckpointConfig.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)

    // 同一时间只允许一个 checkpoint 进行
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)


    //enableIncrementalCheckpointing : 开启增量快照
    // 创建状态后端， rocksDB
    val backend: StateBackend = new RocksDBStateBackend("hdfs://master:9000/flink/checkpoints", true)

    //设置状态后端
    env.setStateBackend(backend)


    //并行度
    //env.setParallelism(2)

    //读取数据
    //nc -lk 8888
    val linesDS: DataStream[String] = env.socketTextStream("master", 8888)


    val countDS: DataStream[(String, Int)] = linesDS
      .flatMap(_.split(","))
      .map((_, 1))
      .keyBy(_._1)
      .sum(1) // 默认就是有状态计算


    //打印
    countDS.print()


    //触发任务执行
    env.execute()

    /**
      *
      *
      * 任务失败之后重新启动任务式需要指定最近一次成功的checkpoint路径
      * -s hdfs://master:9000/flink/checkpoints/38e5262eee8a86d0edd51cd1d60fd5e1/chk-172
      *
      *
      * flink run -c com.shujia.flink.core.Demo7CheckPoint -s hdfs://master:9000/flink/checkpoints/38e5262eee8a86d0edd51cd1d60
      * fd5e1/chk-172 -p 1 flink-1.0.jar
      *
      */

  }

}
