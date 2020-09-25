package com.shujia.dao.impl;

import com.shujia.bean.User;
import com.shujia.dao.UserDao;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * Component 数据持久层的注解
 *
 */

@Component
public class UserDaoImpl implements UserDao {


    private static  Connection con;

    static {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
             con = DriverManager.getConnection("jdbc:mysql://master:3306/user", "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public User queryUserByUsername(String username) {

        User user = null;

        try {
            PreparedStatement stat = con.prepareStatement("select * from t_user where username=?");

            stat.setString(1,username);


            ResultSet resultSet = stat.executeQuery();

            //如果用户不存在，返回null
            if (resultSet.next()){
                String username1 = resultSet.getString("username");
                String password = resultSet.getString("password");

                user = new User(username1,password);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;


    }

    @Override
    public boolean insert(User user) {


        try {
            PreparedStatement stat = con.prepareStatement("insert into t_user(username,password) values(?,?)");

            stat.setString(1,user.getUsername());
            stat.setString(2,user.getPassword());

            int i = stat.executeUpdate();

            //插入成功返回true
            if (i==1){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
