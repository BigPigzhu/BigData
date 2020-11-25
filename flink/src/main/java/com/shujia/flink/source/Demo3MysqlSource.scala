package com.shujia.flink.source

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}

import org.apache.flink.streaming.api.functions.source.SourceFunction

class Demo3MysqlSource extends SourceFunction[String] {
  override def run(ctx: SourceFunction.SourceContext[String]): Unit = {


    Class.forName("com.mysql.jdbc.Driver")
    val con: Connection = DriverManager.getConnection("jdbc:mysql://master:3306/student", "root", "123456")
    val stat: PreparedStatement = con.prepareStatement("select * from student")

    val resultSet: ResultSet = stat.executeQuery()

    while (resultSet.next()) {

      val id: Int = resultSet.getInt("id")
      val age: Int = resultSet.getInt("age")
      val name: String = resultSet.getString("name")
      val gender: String = resultSet.getString("gender")
      val clazz: String = resultSet.getString("clazz")


      //将数据发送到下游
      ctx.collect(s"$id\t$name\t$age\t$gender\t$clazz")

    }

    con.close()


  }

  override def cancel(): Unit = {

  }
}
