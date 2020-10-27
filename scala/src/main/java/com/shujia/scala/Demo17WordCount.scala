package com.shujia.scala

import scala.io.Source

object Demo17WordCount {
  def main(args: Array[String]): Unit = {

    //1、读取数据
    val lines: List[String] = Source.fromFile("scala/data/words.txt").getLines().toList


    //2、使用flatMap 将单词展开
    val words: List[String] = lines.flatMap(line => line.split(","))

    //3、按照单词分组,
    //groupBy  指定分组的列
    val kv: List[(String, List[String])] = words.groupBy(word => word).toList


    //4、统计每个单词的数量
    val counts: List[(String, Int)] = kv.map(kv => {
      val word: String = kv._1

      val ws: List[String] = kv._2

      //统计单词数量
      val size: Int = ws.size

      (word, size)
    })

    counts.foreach(println)
    println("=" * 100)
    //排序
    val sort: List[(String, Int)] = counts.sortBy(kv => -kv._2)

    sort.foreach(println)


    println("=" * 100)

    //在一行中实现
    //链式调用
    Source
      .fromFile("scala/data/words.txt") //读取文件
      .getLines() //获取所有行
      .toList //转换成list
      .flatMap(line => line.split(",")) //将数据展开
      .groupBy(word => word) //安装单词分组
      .toList
      .map(kv => (kv._1, kv._2.size)) //统计每个单词的数量
      .foreach(println)


  }
}
