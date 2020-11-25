package com.shujia.flink.source

import org.apache.flink.streaming.api.functions.source.SourceFunction

/**
  * 自定义source
  *
  * 实现SourceFunction 接口，。 实现run方法
  *
  * run ； 生成数据,  值运行一次
  * cancel ： 回收资源
  *
  */

class Demo2SourceFunaction extends SourceFunction[Int] {

  override def run(ctx: SourceFunction.SourceContext[Int]): Unit = {

    var i: Int = 0

    while (true) {

      //发送数据到下游
      ctx.collect(i)

      Thread.sleep(1000)

      i += 1
    }

  }

  override def cancel(): Unit = {

  }
}
