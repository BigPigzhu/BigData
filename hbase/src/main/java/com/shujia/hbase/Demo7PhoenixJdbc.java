package com.shujia.hbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Demo7PhoenixJdbc {
    public static void main(String[] args) throws Exception {


        //建立连接，  连接zk
        Connection connection = DriverManager.getConnection("jdbc:phoenix:master,node1,node2:2181");


        // 预编译sql执行器
        PreparedStatement stat = connection.prepareStatement("select * from dianxin where mdn=?");


        stat.setString(1, "47BE1E866CFC071DB19D5E1C056BE28AE24C16E7");


        //执行查询
        ResultSet resultSet = stat.executeQuery();


        while (resultSet.next()) {

            String mdn = resultSet.getString("mdn");
            String start_date = resultSet.getString("start_date");
            String end_date = resultSet.getString("end_date");
            String county_id = resultSet.getString("county_id");
            double x = resultSet.getDouble("x");
            double y = resultSet.getDouble("y");

            System.out.println(mdn + "\t" + start_date + "\t" + end_date + "\t" + county_id + "\t" + x + "\t" + y);

        }

        connection.close();


    }
}
