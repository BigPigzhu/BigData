package com.shujia.scala

object Demo26Opt {
  def main(args: Array[String]): Unit = {

    /**
      * map： 对集合中的数据进行处理，传入一行返回一行
      * flatmap： 将数据展开，传入一行返回多行
      * filter : 过滤
      * groupby : 分组
      * sort : 排序
      * foreach: 遍历
      *
      *
      */

    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

    /**
      * map
      *
      */
    val ints: List[Int] = list.map(i => i * 2)
    println(ints)

    //简写
    //如果函数的参数只用了一次，可以使用 _ 代替
    val ints2: List[Int] = list.map(_ * 2)

    /**
      * flatMap
      *
      */

    val lines = List("java,spark", "scala,spark")


    val words1: List[String] = lines.flatMap(line => line.split(","))
    println(words1)

    //简写
    val words2: List[String] = lines.flatMap(_.split("<"))
    println(words2)

    /**
      * filter
      *
      */

    val list2 = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val list4: List[Int] = list2.filter(i => i % 2 == 1)

    println(list4)

    val list5: List[Int] = list2.filter(_ % 2 == 1)
    println(list5)


    /**
      * sort
      *
      */

    val list6 = List(1, 2, 100, 2, 3, 50, 4, 5, 5, 6, 7, 8, 9)

    //sortBy  指定一个排序的列
    //默认式升序
    val sort1: List[Int] = list6.sortBy(i => i)
    println(sort1)

    println("=" * 100)
    //sortWith ： 传入一个比较的规则
    val list7: List[Int] = list6.sortWith((i, j) => i > j)

    println(list7)

    /**
      * groupby
      *
      */
    val list8 = List(1, 2, 100, 2, 3, 50, 4, 5, 5, 6, 7, 8, 9)


    val map: Map[Int, List[Int]] = list8.groupBy(i => i)
    println(map)


  }

}
