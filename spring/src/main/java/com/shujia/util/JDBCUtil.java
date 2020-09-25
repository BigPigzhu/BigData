package com.shujia.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {

    private static Connection con;

    static {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master:3306/user", "root", "123456");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取jdbc连接
     *
     */

    public static Connection getConnection() {
        return con;
    }
}
