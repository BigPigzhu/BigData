package com.shujia.scala;

import java.util.ArrayList;
import java.util.Comparator;

public class Demo1JavaFunaction {

    public static void main(String[] args) {

        ArrayList<Integer> integers = new ArrayList<Integer>();
        integers.add(1);
        integers.add(2);
        integers.add(5);
        integers.add(2);
        integers.add(9);


        //匿名内部类
        integers.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });


        /// lambda 表达式
        //  -> 前面是参数， 后面是函数体和返回值
        //函数式编程
        integers.sort((o1, o2) -> o1 - o2);

        System.out.println(integers);


        //在java中也可以直接调用scala的类
        Demo2Helloworld.main(args);

    }
}
