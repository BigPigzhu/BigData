package com.shujia;

import com.shujia.util.JDBCUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MakeDataStudent {
    public static void main(String[] args) throws Exception {
        /**
         * 生产大量的数据
         *
         */
        //获取连接
        Connection con = JDBCUtil.getConnection();

        FileReader fileReader = new FileReader("spring/data/students.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);



        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(",");
            String id = split[0];
            String name = split[1];
            Integer age = Integer.parseInt(split[2]);
            String gender = split[3];
            String clazz = split[4];

            String sql = "insert into student(id,name,age,gender,clazz) values(?,?,?,?,?)";
            //将数据保存到mysql
            PreparedStatement stat = con.prepareStatement(sql);


            for (int i = 0; i < 1000; i++) {
                String newiD = id + i;


                stat.setString(1,newiD);
                stat.setString(2,name);
                stat.setInt(3,age);
                stat.setString(4,gender);
                stat.setString(5,clazz);


                stat.addBatch();
            }

            stat.executeBatch();

            System.out.println("插入1000条成功");
        }
    }
}
