package com.shujia.TestPool;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;

public class TestMysqlDbcpPool {
    public static void main(String[] args) throws Exception {

        /**
         * 1、创建连接池
         *
         * dbcp：  是准对所有可以使用jdbc链接的数据库
         *
         */

        BasicDataSource basicDataSource = new BasicDataSource();

        //指定链接信息
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://master:3306/user");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("123456");


        //连接池在创建的时候创建几个链接
        basicDataSource.setInitialSize(2);

        //最大链接的数量
        basicDataSource.setMaxIdle(10);


        /**
         * 从连接池中获取链接
         *
         */

        Connection con1 = basicDataSource.getConnection();
        //获取活跃的连接池数量
        System.out.println(basicDataSource.getNumActive());

        //使用链接去操作数据


        //关闭连接，相对于将链接放回连接池
        con1.close();


    }
}
