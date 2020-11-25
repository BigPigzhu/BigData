package com.shujia.flink.source

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala._

object DEmo7RichSource {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val ds: DataStream[String] = env.addSource(new MysqlRichSourceFunaction)

    ds.print()

    env.execute()


  }
}

class MysqlRichSourceFunaction extends RichSourceFunction[String] {
  var con: Connection = _

  /**
    *
    * open ； 每一个并行度中只会调用一次
    * close ：  每一个并行度中只会调用一次
    *
    * opne 在run方法之前被调用 ， close在run方法之后被调用
    *
    */
  override def open(parameters: Configuration): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    con = DriverManager.getConnection("jdbc:mysql://master:3306/student", "root", "123456")
  }

  override def close(): Unit = {
    con.close()
  }

  override def run(ctx: SourceFunction.SourceContext[String]): Unit = {
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
  }

  override def cancel(): Unit = {

  }
}
