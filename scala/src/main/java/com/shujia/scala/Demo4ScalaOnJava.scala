package com.shujia.scala

import java.io.{BufferedReader, FileReader}

import scala.io.{BufferedSource, Source}

object Demo4ScalaOnJava {
  def main(args: Array[String]): Unit = {


    /**
      * 读写文件
      *
      */

    //java中读取文件的方式
    val fileReader: FileReader = new FileReader("scala/data/students.txt")

    val bufferedReader: BufferedReader = new BufferedReader(fileReader)

    //读第一行
    var line: String = bufferedReader.readLine()
    while (line != null) {
      println(line)

      //读下一行
      line = bufferedReader.readLine()
    }


    //scala 中读取文件的方式
    val source: BufferedSource = Source.fromFile("scala/data/students.txt", enc = "utf-8")

    //获取所有行
    //Iterator :迭代器，只能迭代一次
    val lines: Iterator[String] = source.getLines()

    //增强for循环 ，  line 的类型自动推断
    for (line: String <- lines) {
      println(line)
    }

    println("=" * 100)

    for (line: String <- lines) {
      println(line)
    }

  }
}
