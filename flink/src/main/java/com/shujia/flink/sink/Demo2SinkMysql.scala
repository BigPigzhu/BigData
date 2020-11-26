package com.shujia.flink.sink

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala._

object Demo2SinkMysql {

  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(4)

    val linesDS: DataStream[String] = env.readTextFile("flink/data/students.txt")


    linesDS.addSink(new MysqlSink)


    env.execute()

  }

}

class MysqlSink extends RichSinkFunction[String] {

  var con: Connection = _

  override def invoke(value: String, context: SinkFunction.Context[_]): Unit = {
    //执行数据写入
    val split: Array[String] = value.split(",")

    val stat: PreparedStatement = con.prepareStatement("insert into student(id,name,age,gender,clazz) values(?,?,?,?,?)")

    stat.setInt(1, split(0).toInt)
    stat.setString(2, split(1))
    stat.setInt(3, split(2).toInt)
    stat.setString(4, split(3))
    stat.setString(5, split(4))

    stat.execute()

  }

  override def open(parameters: Configuration): Unit = {
    //创建数据库连接
    Class.forName("com.mysql.jdbc.Driver")
    con = DriverManager.getConnection("jdbc:mysql://master:3306/student?useUnicode=true&characterEncoding=UTF-8", "root", "123456")
  }

  override def close(): Unit = {
    //关闭连接
    con.close()
  }
}
