package com.shujia.flink.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Demo1Produce {
  def main(args: Array[String]): Unit = {

    /**
      * 生产者
      *
      */

    val properties: Properties = new Properties

    //咨询参数配置
    properties.setProperty("bootstrap.servers", "master:9092,node1:9092,node2:9092")
    //key value序列化类
    properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    properties.setProperty("transactional.id", "1")

    val prducer: KafkaProducer[String, String] = new KafkaProducer[String, String](properties)


    //1,初始化事务
    prducer.initTransactions()

    //2 开始
    prducer.beginTransaction()

    val java: ProducerRecord[String, String] =
      new ProducerRecord[String, String]("trans", "java")

    //发送数据
    prducer.send(java)


    println("java已发送")
    Thread.sleep(10000)

    val flink: ProducerRecord[String, String] =
      new ProducerRecord[String, String]("trans", "flink")


    //发送数据
    prducer.send(flink)

    //3\提交事务
    prducer.commitTransaction()


    //关闭连接
    prducer.close()

    /**
      *
      * --isolation-level read_committed   读已提交的数据
      *
      * kafka-console-consumer.sh --bootstrap-server  master:9092,node1:9092,node2:9092 --isolation-level read_committed  --topic trans
      *
      */


  }
}
