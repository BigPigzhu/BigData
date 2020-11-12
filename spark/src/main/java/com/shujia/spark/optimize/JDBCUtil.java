package com.shujia.spark.optimize;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    static Connection con;

    static {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master:3306/student?useUnicode=true&characterEncoding=utf-8", "root", "123456");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取jdbc连接
     */

    public static Connection getConnection() {
        return con;
    }
}
