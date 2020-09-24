package com.shujia.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Demo5 {
    public static void main(String[] args) throws Exception{

        /**
         * 通过泛型绕过泛型对象类型的限制
         *
         */

        ArrayList<String> list = new ArrayList<>();

        list.add("java");
        list.add("spark");
        list.add("hadoop");

//        list.add(100)


        Class<ArrayList> listClass = ArrayList.class;

        //获取add方法
        Method add = listClass.getMethod("add", Object.class);

        add.invoke(list,1000);


        System.out.println(list);


    }
}
