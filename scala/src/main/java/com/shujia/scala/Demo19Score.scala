package com.shujia.scala

import scala.io.Source

object Demo19Score {
  def main(args: Array[String]): Unit = {

    /**
      * 2、统计学生的总分
      *
      */

    //1、读取数据
    val lines: List[String] = Source.fromFile("scala/data/score.txt").getLines().toList


    //2、取出学号和分数
    val kvList: List[(String, Int)] = lines.map(line => {
      val split: Array[String] = line.split(",")
      //学号
      val id: String = split(0)

      //分数
      val sco: Int = split(2).toInt

      (id, sco)
    })

    //3、按照学号分组
    //groupBy 指定分组的列
    val map: Map[String, List[(String, Int)]] = kvList.groupBy(kv => kv._1)


    //4、统计学生的总分

    val sumScore: List[(String, Int)] = map.map(kv => {
      val id: String = kv._1

      val socres: List[(String, Int)] = kv._2

      //从socres  取出分数
      val scos: List[Int] = socres.map(kk => kk._2)

      //统计总分
      val sumSco: Int = scos.sum

      (id, sumSco)
    }).toList


    //按照分数降序排序
    val sortScore: List[(String, Int)] = sumScore.sortBy(kv => -kv._2)

    sortScore.foreach(println)

  }

}
