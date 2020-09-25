package com.shujia.service;

import com.shujia.bean.User;

public interface UserService {

    /**
     * 登录的服务
     *
     */
    String login(User user);


    /**
     * 注册的服务
     *
     */

    String register(String username,String password,String newPassword );
}
