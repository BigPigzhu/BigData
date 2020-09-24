package com.shujia.reflection;

import java.lang.reflect.Field;

public class Demo3 {

    public static void main(String[] args)throws Exception {

        /**
         * 可以通过类对象获取类的属性
         *
         */


        Class<Student> aClass = Student.class;

        //获取所有公共属性
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        //通过属性名获取
        Field name = aClass.getField("name");

        System.out.println(name);

        System.out.println("==============");


        //获取所有的属性
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }

        //获取属性名获取
        Field age = aClass.getDeclaredField("age");
        System.out.println(age);


    }
}
