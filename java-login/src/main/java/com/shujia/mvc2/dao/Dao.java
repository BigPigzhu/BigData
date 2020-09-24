package com.shujia.mvc2.dao;

import com.shujia.mvc2.User;


/**
 * 将所需要实现的方法抽象成接口
 *
 * 可以使用多态
 *
 */
public interface Dao {
    public User queryUserByUsername(String username);
    public Boolean inserUser(User user);
}
