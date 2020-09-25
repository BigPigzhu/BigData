package com.shujia.service.impl;

import com.shujia.bean.User;
import com.shujia.dao.UserDao;
import com.shujia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Service : 业务层注解，spring boot 自动创建对象
 *
 */
@Service
public class UserServiceImpl implements UserService {


    /**
     * Autowired  ; 依赖注入
     * spring boot 回去根据类型，自动给属性赋值
     *
     * 底层使用的是反射
     *
     */
    @Autowired
    private UserDao userDao ;

    @Override
    public String login(User user) {


        /**
         * 判断用户名和密码是否为空
         *
         */

        if (user.getUsername().trim().isEmpty() | user.getPassword().trim().isEmpty()){
            return "用户名密码不能为空";
        }

        /**
         * 判断用户名是否存在
         *
         */
        User user1 = userDao.queryUserByUsername(user.getUsername());

        if (user1==null){
            return "用户名不存在";
        }

        /**
         * 判断密码是否正确
         *
         */

        if (! user.getPassword().equals(user1.getPassword())){
            return "密码不正确";
        }

        return "登录成功";


    }

    @Override
    public String register(String username, String password, String newPassword) {


        /**
         * 判断用户名密码是否为空
         *
         */

        if (username.trim().isEmpty() | password.trim().isEmpty()| newPassword.trim().isEmpty()){
            return "用户名密码不能为空";
        }

        /**
         * 判断密码是否一致
         *
         */

        if (! password.equals(newPassword)){
            return "密码不一致";
        }

        /**
         * 判断用户是否存在
         *
         */

        User user = userDao.queryUserByUsername(username);
        if (user!=null){
            return "用户已存在";
        }


        /**
         * 插入数据
         *
         */

        boolean flag = userDao.insert(new User(username, password));

        if (! flag){
            return "注册失败";
        }

        return "注册成功";
    }
}
