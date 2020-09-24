package com.shujia.mvc1;

import java.sql.*;

/**
 * 持久层，和数据库打交道
 *
 */
public class Dao {

    /**
     * 通过用户名查询用户
     *
     */

    private static   Connection con;

    static {
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



}
