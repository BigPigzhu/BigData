package com.shujia.dao.impl;

import com.shujia.bean.Location;
import com.shujia.dao.DianXInDao;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class DianXInDaoImpl implements DianXInDao {


    private static Connection connection;

    static {
        try {
            //创建连接
            connection = DriverManager.getConnection("jdbc:phoenix:master,node1,node2:2181");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Location> queryLocationByMdn(String mdn, int limit) {

        ArrayList<Location> locations = new ArrayList<>();


        String sql = "select x,y,start_date,end_date from dianxin where mdn=? order by  START_DATE desc limit ?";

        try {
            PreparedStatement stat = connection.prepareStatement(sql);
            stat.setString(1, mdn);
            stat.setInt(2, limit);

            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()) {
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                String start_date = resultSet.getString("start_date");
                String end_date = resultSet.getString("end_date");


                Location location = new Location(x, y, start_date, end_date);
                locations.add(location);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locations;

    }

    @Override
    public List<Location> queryLocationByMdnAndCityId(String mdn, String city) {

        ArrayList<Location> locations = new ArrayList<>();
        try {

            String sql = "select x,y,start_date,end_date from dianxin where mdn=? and county_id=?";
            PreparedStatement stat = connection.prepareStatement(sql);


            stat.setString(1, mdn);
            stat.setString(2, city);

            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()) {
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                String start_date = resultSet.getString("start_date");
                String end_date = resultSet.getString("end_date");


                Location location = new Location(x, y, start_date, end_date);
                locations.add(location);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locations;
    }
}
