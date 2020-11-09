package com.shujia.spark.stream

import java.util.{Date, Properties}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

object Demo11MakeData {

  def main(args: Array[String]): Unit = {


    val properties: Properties = new Properties()
    //咨询参数配置
    properties.setProperty("bootstrap.servers", "master:9092,node1:9092,node2:9092")
    //key value序列化类
    properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    //创建kafka生产者
    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](properties)


    while (true) {

      //每个疫苗发送一条数据
      Thread.sleep(1000)

      //商品id
      val id: Int = Random.nextInt(100)

      //购买的时间
      val time: Long = new Date().getTime

      //商品价格
      val pic: Int = Random.nextInt(1000)

      val line: String = s"$id,$time,$pic"


      val record: ProducerRecord[String, String] = new ProducerRecord[String, String]("item", line)


      //发送到kafka
      producer.send(record)

    }

  }

}
