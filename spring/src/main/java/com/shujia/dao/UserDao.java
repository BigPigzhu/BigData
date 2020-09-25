package com.shujia.dao;

import com.shujia.bean.User;

public interface UserDao {

    /**
     * 通过用户名查询用户额接口
     *
     */

    User queryUserByUsername(String username);


    /**
     * 插入一条数据
     *
     */
    boolean insert(User user);
}
