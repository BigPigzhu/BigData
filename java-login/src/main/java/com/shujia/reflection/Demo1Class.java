package com.shujia.reflection;

public class Demo1Class {

    public static void main(String[] args) throws Exception{


        /**
         * 获取类对象的方式
         *
         * 所有的类被加载到内存中后都是Class这个类的一个对象 称为类对象
         *
         */

        //通过类名获取
        Class<?> aClass = Class.forName("com.shujia.reflection.Student");

        //通过类获取类对象
        Class<Student> studentClass = Student.class;

        //通过类的对象获取类对象
        Student student = new Student("aa",1);

        Class aClass1 = student.getClass();


        System.out.println(aClass == studentClass);

        System.out.println(aClass == aClass1);
    }
}
