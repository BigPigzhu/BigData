package com.shujia.scala

import scala.io.Source

// 导入隐式转换
import com.shujia.scala.a._

object Demo24Implicit {
  def main(args: Array[String]): Unit = {

    val path: String = "scala/data/students.txt"


    //    val read = new Read(path)
    //    val lines: List[String] = read.read()

    /**
      * String 类型会被隐式转换成Read
      *
      */

    val strings: List[String] = path.read()

    println(strings)




  }

}


object a {

  /**
    * 隐式转换类
    *
    * 所有的String类型都会被隐式转换成Read类型
    *
    */

  implicit class Read(path: String) {

    println("Read的默认构造函数")

    def read(): List[String] = {
      val lines: List[String] = Source.fromFile(path).getLines().toList
      lines
    }
  }

}



