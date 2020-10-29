package com.shujia.scala

object Demo20Match {
  def main(args: Array[String]): Unit = {

    /**
      * 在java中模式匹配，只能匹配（基本数据类型，字符串，枚举）
      *
      * scala中的模式匹配可以匹配基本数据类型，字符串，美剧，对象，类型
      *
      */

    val str: String = "男"

    /**
      * 模式匹配：
      * =>  前面式匹配项， 后面式匹配成功之后执行的代码块
      * _ 通配
      **/

    //匹配字符串
    str match {
      case "男" => {
        println("性别为男")
      }
      case "女" => println("性别为女")
      case _ => println("默认值")
    }


    // 匹配基本数据类型

    val age = 20

    age match {
      case 20 => println("年龄等于20")
    }

    age >= 18 match {
      case true => println("成年")
      case false => println("未成年")
    }

    // 匹配对象

    val user = User("张三", 23)

    user match {
      case User("李四", 23) => println("李四")
      case User("张三", 23) => println("张三")
    }


    //匹配类型
    val user1: Any = 1

    user1 match {
      case s: String => println("是一个字符串：" + s)
      case u: User => println("是一个user:" + u)
      case _ => println("其他类型")
    }


  }

  case class User(name: String, age: Int)

}
