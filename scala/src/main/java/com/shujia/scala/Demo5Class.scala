package com.shujia.scala

object Demo5Class {

  def main(args: Array[String]): Unit = {

    val student: Student = new Student("001", "张三", 23, "文科一班")

    println(student)
    println(student._age)

    //修改属性的值，属性需要同各国var定义
    student._name = "李四"

    println(student)
  }

}

//(id: String, name: String) 默认构造函数的参数
class Student(id: String, name: String, age: Int) {

  println("默认构造函数")

  //私有化
  private val _id: String = id

  var _name: String = name

  val _age: Int = age

  //_  占位符
  var _clazz: String = _


  //重置构造函数，第一行必须显示调用默认构造函数
  def this(id: String, name: String, age: Int, clazz: String) {
    this(id, name, age)
    println("重置构造函数")

    _clazz = clazz
  }


  //重写父类的方法
  override def toString = s"Student(${_id}, ${_name}, ${_age})"
}
