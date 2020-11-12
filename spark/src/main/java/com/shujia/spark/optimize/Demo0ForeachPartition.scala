package com.shujia.spark.optimize

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object Demo0ForeachPartition {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("foreach")
      .getOrCreate()

    val rdd1: RDD[String] = spark
      .sparkContext
      .textFile("spark/data/students.txt", 4)

    println(rdd1.getNumPartitions)

    /**
      * 架构rdd的数据保存到mysql中
      *
      */

    /*

        rdd1.foreach(line => {

          /**
            * 建立jdbc李连杰
            *
            * 啊网络连接的对象不能序列化，也不能在网络中传输
            *
            */
          Class.forName("com.mysql.jdbc.Driver")
          val con: Connection = DriverManager.getConnection("jdbc:mysql://master:3306/student?useUnicode=true&characterEncoding=utf-8", "root", "123456")


          val stat: PreparedStatement = con.prepareStatement("insert into student(id,name,age,gender,clazz) values(?,?,?,?,?)")


          val split: Array[String] = line.split(",")

          stat.setString(1, split(0))
          stat.setString(2, split(1))
          stat.setInt(3, split(2).toInt)
          stat.setString(4, split(3))
          stat.setString(5, split(4))


          //插入数据
          stat.executeUpdate()

        })
    */


    /**
      * foreachPartition  : 遍历一个分区的数据
      *
      * iter ： 一个分区的数据
      *
      *
      * 如果需要将数据保存到外部数据库，使用foreachPartition 代替foreach
      *
      * foreachPartition 每一个分区只会创建一个连接
      */
    rdd1.foreachPartition(iter => {

      //这里的代码每一个分区指挥执行一次

      //每一个分区只会建立一个连接
      Class.forName("com.mysql.jdbc.Driver")
      val con: Connection = DriverManager.getConnection("jdbc:mysql://master:3306/student?useUnicode=true&characterEncoding=utf-8", "root", "123456")

      println("连接建立成功")

      //遍历一个分区的数据
      iter.foreach(line => {
        val stat: PreparedStatement = con.prepareStatement("insert into student(id,name,age,gender,clazz) values(?,?,?,?,?)")


        val split: Array[String] = line.split(",")

        stat.setString(1, split(0))
        stat.setString(2, split(1))
        stat.setInt(3, split(2).toInt)
        stat.setString(4, split(3))
        stat.setString(5, split(4))


        //插入数据
        stat.executeUpdate()
      })

      //关闭连接
      con.close()

    })

  }

}
