package com.shujia.mvc2.service;


import com.shujia.mvc2.User;
import com.shujia.mvc2.dao.Dao;

/**
 * 业务层： 编写约为代码
 */

public class ServiceImpl2 implements Service{

    /**
     * 多态
     *
     * 父类的引用指向子类的对象
     *
     */
    private Dao dao;

    public ServiceImpl2(Dao dao) {
        this.dao = dao;
    }

    public String login(String username, String password) {

        if (username.trim().isEmpty()) {
            return "用户名不能为空";
        }

        if (password.trim().isEmpty()) {
            return "密码不能为空";
        }

        /**
         * 判断用户米是否存在
         *
         */


        //通过用户米查询用户是否存在
        User user = dao.queryUserByUsername(username);

        if (user == null) {
            return "用户米不存在。";
        }

        //如果用户存在，判断密码是否正确

        if (password.equals(user.getPassword())){
            return "登录成功";
        }


        return "用户名和密码不正确";
    }

    public String register(String username, String password, String newpassword) {

        /**
         *
         * 1、判断用户名密码不为空
         * 判断密码是否一致
         *
         */
        if (username.trim().isEmpty()){
            return "用户名不能为空";
        }

        if (password.trim().isEmpty()  | newpassword.trim().isEmpty()){
            return "密码不能为空";
        }

        if (! password .equals(newpassword)){
            return "密码不一致";
        }


        /**
         * 判断用户是否存在
         *
         */


        User user = dao.queryUserByUsername(username);

        if (user!=null){
            return "用户已存在";
        }


        /**
         * 插入新用户
         *
         */

        Boolean flag = dao.inserUser(new User(username, password));

        if (!flag){
            return "注册失败";
        }

        return "注册成功";
    }

}
