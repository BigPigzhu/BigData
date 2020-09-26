package com.shujia.dao.impl;

import com.shujia.bean.Student;
import com.shujia.dao.CacheDao;
import com.shujia.util.JDBCUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CacheDaoImpl implements CacheDao {


    /**
     * 通过该学号查询学生信息
     */
    @Override
    public Student queryStudentBuId(String id) {

        Student student = new Student();

        // 获取jdbc链接
        Connection con = JDBCUtil.getConnection();

        try {
            PreparedStatement stat = con.prepareStatement("select * from student where id=?");

            stat.setString(1, id);

            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String clazz = resultSet.getString("clazz");

                student.setId(id);
                student.setName(name);
                student.setAge(age);
                student.setGender(gender);
                student.setClazz(clazz);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }
}
