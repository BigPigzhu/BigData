package com.shujia.TestPool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

public class TEstMysqlC3p0Pool {
    public static void main(String[] args) throws Exception {
        /**
         * 创建c3p0 连接池
         *
         */

        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://master:3306/user");
        dataSource.setUser("root");
        dataSource.setPassword("123456");

        dataSource.setInitialPoolSize(2);
        dataSource.setMaxPoolSize(10);


        //获取链接

        Connection connection = dataSource.getConnection();


        connection.close();

    }
}
