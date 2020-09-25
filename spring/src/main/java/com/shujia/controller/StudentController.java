package com.shujia.controller;


import com.shujia.bean.Student;
import com.shujia.util.JDBCUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class StudentController {

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

                //spring boot 会自动将自定义类的对象转换成json格式的字符串返回
                return student;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }



        return null;
    }


}
