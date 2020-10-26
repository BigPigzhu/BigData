package com.shujia.scala

object Demo8Apply {
  def main(args: Array[String]): Unit = {
    Student3.print()
    Student3.apply("java")

    //通过伴生对象，去掉new关键字
    val student: Student3 = Student3("张三")

    println(student)

  }
}

class Student3(name: String) {

  var _name: String = name


  override def toString = s"Student3(${_name})"
}


/**
  * object中的方法都是静态方法，可以直接通过类名调用
  * apply 是一个特殊的方法，可以直接通过类名 加() 调用
  *
  */

// 伴生对象
object Student3 {

  def print(): Unit = {
    println("student3")
  }

  def apply(name: String): Student3 = new Student3(name)
}
