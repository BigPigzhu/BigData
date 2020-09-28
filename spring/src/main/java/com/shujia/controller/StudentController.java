package com.shujia.controller;


import com.shujia.bean.Student;
import com.shujia.bean.SumScore;
import com.shujia.util.JDBCUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.HashMap;

@RestController
public class StudentController {

    /**
     * 将已经查询过的数据保存到cache中
     *
     * 使用HashMap做缓存存在的问题
     * 1、当数据量越来越来hashMap效率会降低
     * 2、没办法清除没有用的缓存
     *
     *
     */

    HashMap<String,Student> cache = new HashMap<String,Student>();


    /**
     * 通过学号查询学生信息的接口
     *
     * http://192.168.0.197:8080/queryStudentBuId?id=1500100127&key=123456
     *
     */

    @GetMapping("/queryStudentBuId")
    public Student queryStudentBuId(String id,String key){

        if (! "123456".equals(key)){
            return null;
        }


        /**
         * 1、先查询缓存
         *
         * 如果缓存中有数据，直接返回，如果没有再查询数据库
         */

        Student student1 = cache.get(id);
        if (student1!=null){

            System.out.println("缓存中有数据，直接返回");
            return student1;
        }

        System.out.println("缓存中没有数据查询数据库");

        /**
         * 连接数据库查询学生信息
         *
         */

        try {
            //获取连接
            Connection con = JDBCUtil.getConnection();

            PreparedStatement stat = con.prepareStatement("select * from student where id=?");

            stat.setString(1,id);

            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()){
                String id1 = resultSet.getString("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String clazz = resultSet.getString("clazz");


                Student student = new Student(id1,name,age,gender,clazz);

                /**
                 * 2、将查询回来的结果保存到缓存中
                 *
                 */

                cache.put(id,student);


                //spring boot 会自动将自定义类的对象转换成json格式的字符串返回
                return student;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }



        return null;
    }





}
