package com.shujia.reflection;

import java.lang.reflect.Constructor;

public class Demo2 {

    public static void main(String[] args) throws Exception{

        /**
         * 类对象的使用
         *
         * 1、铜鼓欧蕾对象创建对象
         *
         */

        Class<?> aClass = Class.forName("com.shujia.reflection.Student");

        //调用无参构造函数创建对象
        Object o = aClass.newInstance();

        System.out.println(o);


        /**
         * 2、通过获取构造函数的对象类创建类的对象
         *
         */

        Constructor<?> constructor = aClass.getConstructor(String.class, Integer.class);

        Object zs = constructor.newInstance("张三", 23);

        System.out.println(zs);


    }
}
