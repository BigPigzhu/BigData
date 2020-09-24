package com.shujia.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Demo1Login {
    public static void main(String[] args) throws Exception {

        /**
         * 键盘输入用户名和密码
         *
         */

        Scanner scanner = new Scanner(System.in);


        System.out.println("请输入用户名：");

        //用户名
        String username = scanner.next();

        if (username.trim().isEmpty()){
            System.out.println("用户名不能为空...");
        }

        System.out.println("请输入用密码：");

        //密码
        String password = scanner.next();

        if (password.trim().isEmpty()){
            System.out.println("密码不能为空...");
        }


        /**
         * 连接数据库判断用户名和密码是否正确
         * 需要到jdbs依赖
         *
         */


        //1、加载驱动

        Class.forName("com.mysql.jdbc.Driver");


        //2、创建连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://master:3306/user", "root", "123456");


        //3、查询,  判断用户名和密码是否正确
        PreparedStatement stat = conn.prepareStatement("select * from t_user where username=? and password=?");

        stat.setString(1,username);

        stat.setString(2,password);

        //执行查询
        ResultSet resultSet = stat.executeQuery();

        //如果用户名密码正确返回值结果中就有数据
        if (resultSet.next()){
            String username1 = resultSet.getString("username");
            String password1 = resultSet.getString("password");

            System.out.println("登录成功："+username1+","+password1);
        }else {
            System.out.println("用户名密码不正确");
        }

    }
}
