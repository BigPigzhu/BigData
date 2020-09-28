package com.shujia.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    private static BasicDataSource basicDataSource;

    static {

//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://master:3306/user?useUnicode=true&characterEncoding=utf-8", "root", "123456");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 创建连接池
         *
         */

         basicDataSource = new BasicDataSource();

        //指定链接信息
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://master:3306/user");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("123456");


        //连接池在创建的时候创建几个链接
        basicDataSource.setInitialSize(2);

        //最大链接的数量
        basicDataSource.setMaxIdle(10);
    }

    /**
     * 获取jdbc连接
     */

    public static Connection getConnection() {
        Connection connection = null;
        try {
            //冲连接池中获取链接，如果连接池中，有链接，直接返回，如果没有就创建链接
            connection = basicDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
