package com.shujia.flink.sink

import java.util.concurrent.TimeUnit

import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.DateTimeBucketAssigner
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy
import org.apache.flink.streaming.api.scala._

object Demo3SinkFile {

  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val ds: DataStream[String] = env.socketTextStream("master", 8888)


    /**
      * withRollingPolicy ; 文件生成策略
      * 它至少包含 15 分钟的数据
      * 最近 5 分钟没有收到新的记录
      * 文件大小达到 1GB （写入最后一条记录后）
      *
      * withBucketAssigner :目录生成方式
      *
      */

    val sink: StreamingFileSink[String] = StreamingFileSink
      .forRowFormat(new Path("flink/data/out3"), new SimpleStringEncoder[String]("UTF-8"))
      .withRollingPolicy(
        DefaultRollingPolicy.builder()
          .withRolloverInterval(TimeUnit.MINUTES.toMillis(15))
          .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
          .withMaxPartSize(1024 * 1024 * 1024)
          .build())
      .withBucketAssigner(new DateTimeBucketAssigner[String]("yyyyMMdd"))
      .build()


    ds.addSink(sink)


    env.execute()


  }

}
