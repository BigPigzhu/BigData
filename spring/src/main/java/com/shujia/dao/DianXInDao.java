package com.shujia.dao;

import com.shujia.bean.Location;

import java.util.List;

public interface DianXInDao {


    //通过手机号查询最新的10个位置
    List<Location> queryLocationByMdn(String mdn,int limit);


    //查询用户在同一个城市冲所有的位置
    List<Location> queryLocationByMdnAndCityId(String mdn, String city);


}
