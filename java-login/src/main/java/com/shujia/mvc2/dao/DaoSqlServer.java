package com.shujia.mvc2.dao;

import com.shujia.mvc2.User;

import java.sql.*;

/**
 * 持久层，和数据库打交道
 *
 */
public class DaoSqlServer implements Dao{

    /**
     * 通过用户名查询用户
     *
     */

    private static   Connection con;

    static {

        System.out.println("sqlserver数据库");

        //1、加载驱动

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //2、创建连接
        try {
             con = DriverManager.getConnection("jdbc:mysql://master:3306/user", "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过用户米查询用户
     *
     * 如果用户存在，返回哟过户的数据
     * 如果用户不存在返回null
     *
     */


    public User queryUserByUsername(String username){
        User user = null;
        try {
            PreparedStatement stat = con.prepareStatement("select * from t_user where username=?");
            stat.setString(1,username);

            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()) {
                user = new User();
                String username1 = resultSet.getString("username");
                String password = resultSet.getString("password");

                user.setUsername(username1);
                user.setPassword(password);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    /**
     * 插入一条数据
     *
     * 插入成功返回true
     * 插入失败返回false
     */
    public Boolean inserUser(User user){
        try {
            PreparedStatement stat = con.prepareStatement("insert into t_user(username,password) values(?,?)");

            stat.setString(1,user.getUsername());
            stat.setString(2,user.getPassword());

            int i = stat.executeUpdate();
            if (i==1){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
