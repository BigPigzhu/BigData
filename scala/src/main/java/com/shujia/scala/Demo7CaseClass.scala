package com.shujia.scala

object Demo7CaseClass {
  def main(args: Array[String]): Unit = {

    //样例 类可以不通过new 创建对象
    val student: Studnet2 = Studnet2("张三", 23, "文科一班")

    println(student)
    //可以直接通过属性民操作属性
    println(student.age)

  }
}

/**
  *
  * 样例类
  * scala再编译的过程中会自动再类上增加 常用的方法
  *
  */
case class Studnet2(name: String, age: Int, clazz: String)

