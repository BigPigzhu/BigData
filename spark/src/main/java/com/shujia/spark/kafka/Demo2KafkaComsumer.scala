package com.shujia.spark.kafka

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object Demo2KafkaComsumer {
  def main(args: Array[String]): Unit = {


    val properties = new Properties()

    //指定kafkajiqun的地址
    properties.setProperty("bootstrap.servers", "master:9092,node1:9092,node2:9092")
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    //消费者组编号
    properties.setProperty("group.id", "asdasdasd")


    //从哪里读数据
    /**
      * earliest
      * 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
      * latest
      * 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
      *
      */
    properties.put("auto.offset.reset", "earliest")

    //zk连接超时时间
    properties.put("zookeeper.session.timeout.ms", "10000")

    //zk同步时间
    properties.put("zookeeper.sync.time.ms", "200")

    //自动提交偏移量间隔时间
    properties.put("auto.commit.interval.ms", "1000")


    //创建消费者
    val comsumer = new KafkaConsumer[String, String](properties)


    //订阅topic
    comsumer.subscribe(util.Arrays.asList("test_topic1"))


    while (true) {


      //等待获取消息
      val consumerRecords: ConsumerRecords[String, String] = comsumer.poll(1000)

      import scala.collection.JavaConversions._

      //获取消息的迭代器
      consumerRecords
        .iterator()
        .toList
        .foreach(record => {
          val key: String = record.key()
          val value: String = record.value()
          val offset: Long = record.offset()
          val partition: Int = record.partition()
          val topic: String = record.topic()


          println(s"$key\t$value\t$offset\t$partition\t$topic")

        })
    }

  }
}
