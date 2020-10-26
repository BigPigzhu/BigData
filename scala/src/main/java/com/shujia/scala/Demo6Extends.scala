package com.shujia.scala

object Demo6Extends {
  def main(args: Array[String]): Unit = {

    val student = new Student1("张三", 23, "文科一班")

    println(student)


    //多态
    val lisi: Person = new Student1("李四", 23, "文科一班")

    println(lisi)

  }
}

class Person(name: String, age: Int) {

  println("Person类默认构造函数")
  var _name: String = name
  var _age: Int = age

  override def toString = s"Person(${_name}, ${_age})"
}


/**
  * 继承
  * 再继承的时候默认需要调用父类的构造函数
  *
  */

class Student1(name: String, age: Int, clazz: String) extends Person(name, age) {
  println("Student默认构造函数")
  var _clazz: String = clazz

  //重写父类的方法
  override def toString = s"Student1(${_name}\t${_age}\t${_clazz})"
}
