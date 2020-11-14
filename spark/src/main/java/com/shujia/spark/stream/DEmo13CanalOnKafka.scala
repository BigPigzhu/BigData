package com.shujia.spark.stream

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, SparkSession}

object DEmo13CanalOnKafka {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .master("local[8]")
      .appName("canal")
      .config("spark.sql.shuffle.partitions", 1)
      .getOrCreate()

    import spark.implicits._


    val kafkaDF: DataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "master:9092,node1:9092,node2:9092") //kafka 集群地址
      .option("subscribe", "example")
      .option("startingOffsets", "latest") // latest : 只读最新的数据,  earliest : 读所有数据
      .load()

    val dataDF: DataFrame = kafkaDF
      .selectExpr("cast(value as string) as value")
      .as[String]
      .filter(line => {
        val json: JSONObject = JSON.parseObject(line)
        val table: String = json.getString("table")
        //过滤数据
        "student".equals(table)
      })
      .map(line => {
        val json: JSONObject = JSON.parseObject(line)

        //获取操作类型
        val t: String = json.getString("type")

        //解析数据
        val dataArray: JSONArray = json.getJSONArray("data")

        val data: JSONObject = dataArray.getJSONObject(0)

        val id: Integer = data.getInteger("id")
        val name: String = data.getString("name")
        val age: Integer = data.getInteger("age")
        val gender: String = data.getString("gender")
        val clazz: String = data.getString("clazz")

        (id, name, age, gender, clazz, t)
      }).toDF("id", "name", "age", "gender", "clazz", "type")


    /**
      * 实时统计班级学生的人数
      *
      */
    dataDF.createOrReplaceTempView("student")

    val countDF: DataFrame = spark.sql(
      """
        |select
        |clazz,sum(
        |case
        |when type='INSERT' then 1
        |when type='DELETE' then -1
        |else 0 end
        |)  as s
        |from student  group by clazz
        |
      """.stripMargin)


    countDF.writeStream
      .format("console")
      .outputMode(OutputMode.Complete())
      .start()
      .awaitTermination()


  }
}
