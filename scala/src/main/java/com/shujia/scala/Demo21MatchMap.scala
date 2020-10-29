package com.shujia.scala

object Demo21MatchMap {
  def main(args: Array[String]): Unit = {

    val map = Map("001" -> "张三", "002" -> "李四")

    //通过key获取value
    println(map("001"))
    println(map.getOrElse("001", "默认值"))
    println("=" * 100)

    /**
      * Option : 有两个取值
      * Some  有值
      * None： 空
      *
      */

    val option: Option[String] = map.get("004")


    //通过模式匹配避免空指针异常
    //match  可以有返回值
    val value: String = option match {
      case None => "默认值" // 没有取到的时候返回默认值
      case Some(v) => v //如果取到了就返回对应的值
    }

    println(value)

    //等同于
    println(map.getOrElse("004", "默认值"))


  }
}
