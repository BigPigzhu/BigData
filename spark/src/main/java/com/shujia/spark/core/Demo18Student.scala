package com.shujia.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo18Student {
  def main(args: Array[String]): Unit = {

    /**
      *
      * 1、统计年级排名前十学生各科的分数 [学号,学生姓名，学生班级，科目名，分数]
      */

    val conf: SparkConf = new SparkConf().setMaster("local[8]").setAppName("opt")

    val sc = new SparkContext(conf)


    //读取分数表
    val scoreRDD: RDD[String] = sc.textFile("spark/data/score.txt")
    val studentRDD: RDD[String] = sc.textFile("spark/data/students.txt")
    val courceRDD: RDD[String] = sc.textFile("spark/data/cource.txt")


    //1  统计学生总分
    val kvScore: RDD[(String, Double)] = scoreRDD.map(line => {
      val split: Array[String] = line.split(",")
      val id: String = split(0)
      val sco: Double = split(2).toDouble
      (id, sco)
    })

    //通过学号对分数求和
    val sumScore: RDD[(String, Double)] = kvScore.reduceByKey(_ + _)


    //2  获取年级排名前十的学生

    // 按照总分拍降序
    val sortSumScore: RDD[(String, Double)] = sumScore.sortBy(_._2, ascending = false)

    //取前十
    val tuples: Array[(String, Double)] = sortSumScore.take(10)

    //取出学号
    val top10Ids: Array[String] = tuples.map(kv => kv._1)


    //3  取出前十学生所有分数
    val top10Score: RDD[String] = scoreRDD.filter(line => {
      val id: String = line.split(",")(0)
      top10Ids.contains(id)
    })


    //4  关联学生和科目表,整理数据


    //先关联学生表
    val topKVScore: RDD[(String, String)] = top10Score.map(line => {
      val id: String = line.split(",")(0)
      (id, line)
    })

    val kvStudent: RDD[(String, String)] = studentRDD.map(line => (line.split(",")(0), line))


    val joinRDD1: RDD[(String, (String, String))] = topKVScore.join(kvStudent)


    //整理数据
    //[学号,学生姓名，学生班级，科目名，分数]
    val scoStuRDD: RDD[(String, (String, String, String, Double))] = joinRDD1.map(kv => {
      val id: String = kv._1

      val sco: String = kv._2._1

      val scoSplit: Array[String] = sco.split(",")
      //科目编号
      val cou_id: String = scoSplit(1)

      //每科的分数
      val score: Double = scoSplit(2).toDouble

      val student: String = kv._2._2

      val stuSplit: Array[String] = student.split(",")
      //姓名
      val name: String = stuSplit(1)
      //班级
      val clazz: String = stuSplit(4)


      val value = (id, name, clazz, score)


      //以科目编号作为key
      (cou_id, value)
    })


    //关联科目表
    val couKVRDD: RDD[(String, String)] = courceRDD.map(line => {
      val id: String = line.split(",")(0)
      val name: String = line.split(",")(1)
      (id, name)
    })


    val joinRDD2: RDD[(String, ((String, String, String, Double), String))] = scoStuRDD.join(couKVRDD)


    //整理数据
    val resultRDD: RDD[String] = joinRDD2.map(kv => {
      val scoStu: (String, String, String, Double) = kv._2._1

      val couName: String = kv._2._2

      val id: String = scoStu._1
      val name: String = scoStu._2
      val clazz: String = scoStu._3

      val score: Double = scoStu._4


      //返回
      s"$id,$name,$clazz,$couName,$score"

    })

    //通过学号排序
    val sortRDD : RDD[String] = resultRDD.sortBy(_.split(",")(0))




    sortRDD.saveAsTextFile("spark/data/top10")


  }
}
