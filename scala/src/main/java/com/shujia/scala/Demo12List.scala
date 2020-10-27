package com.shujia.scala

object Demo12List {
  def main(args: Array[String]): Unit = {

    /**
      * scala中集合分为
      * list  : 有序（插入顺序），可以重复
      * Set : 无序，唯一
      * Map : key-value
      * Tuple：
      */


    //List  : 默认是一个不可变的集合, 相当于java中的数组
    val list: List[Int] = List(1, 2, 3, 4, 100, 5, 6, 7, 8)


    println(list.sum) //求和
    println(list.max) //获取最大值
    println(list.min) //最小值
    println(list.size) //获取集合长度
    println(list.head) //取第一个元素
    println(list.last) //取最后一个
    println(list.tail) //获取不包含第一个元素的其他所有元素
    println(list.take(2)) //获取前面的袁术
    println(list.takeRight(2)) //获取后面的元素
    println(list.reverse) //反转
    println(list.mkString("|")) // 功能和split 相反
    println(list.drop(3)) //删除前面几个元素，不在在选集合上删除，返回一个新的集合
    println(list.dropRight(2))
    println(list)

    println(list.isEmpty) //判断是否为空

    println(list.nonEmpty) //相反

    println(list.contains(100)) //判断是否包含某个袁术


    println(list(1)) //通过下标获取元素

    println("=" * 100)

    /**
      * 集合的遍历
      *
      */

    //增强for循环
    for (l <- list) {
      println(l)
    }

    println("=" * 100)

    def fun(i: Int): Unit = {
      println(i)
    }

    //以函数作为参数
    list.foreach(fun)
    println("=" * 100)

    //i => println(i)  匿名函数
    //i  代表的时候集合中的每一个元素
    list.foreach(i => println(i))

    println("=" * 100)
    //简写
    list.foreach(println)

    println("=" * 100)

    //在foreache函数内部也可以使用外面的变量
    var v = 100
    list.foreach(i => {
      var k = v + i
      println(k)
    })

    println("=" * 100)

    //foreach 可以嵌套
    list.foreach(i => {
      list.foreach(j => {
        println(i * j)
      })
    })

    println("=" * 100)
    /**
      *
      * map 函数, 返回一个新的集合
      *
      * 将集合中每一个元素传递给后面的函数，最终返回一个新的集合
      * i : 是集合中的每一个元素
      *
      * 集合中有多少个元素 i => i + 1  函数就会被调用多少次
      */

    val ints: List[Int] = list.map(i => i + 1)
    println(ints)

    println("=" * 100)
    /**
      * flatmap: 扁平化，  内部就是两层循环，  将集合展开
      * 传入一行返回多行
      * 1、map操作
      * 2、flat 操作
      *
      */

    val lines = List("java,spark,python", "hadoop,hbase,java", "java,scala")


    lines.foreach(line => {
      val arr: Array[String] = line.split(",")
      arr.foreach(word => {
        println(word)
      })
    })
    println("=" * 100)

    println(lines.flatMap(line => line.split(",")))


  }

}
