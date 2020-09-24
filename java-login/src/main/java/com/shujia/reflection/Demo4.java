package com.shujia.reflection;

import java.lang.reflect.Method;

public class Demo4 {
    public static void main(String[] args) throws Exception{

        /**
         * 获取类的方法
         *
         */

        Student student = new Student();

        Class<Student> studentClass = Student.class;

        //获取方法名和参数类型获取方法对象
        Method method = studentClass.getDeclaredMethod("setName", String.class);


        //调用方法
        method.invoke(student,"张三");

        System.out.println(student);

    }
}
