package com.shujia.spark.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.io.Source

object Demo1KafkaProduce {
  def main(args: Array[String]): Unit = {

    /**
      * 键列连接
      * 创建生产者
      *
      */

    val properties = new Properties()

    //指定kafkajiqun的地址
    properties.setProperty("bootstrap.servers", "master:9092,node1:9092,node2:9092")
    properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    //创建生产者

    val producer = new KafkaProducer[String, String](properties)


    Source
      .fromFile("spark/data/students.json")
      .getLines()
      .foreach(line => {

        //构建一行数据
        val record = new ProducerRecord[String, String]("test_topic1", line)

        //生产数据
        producer.send(record)

      })


    //关闭连接
    producer.close()


  }
}
