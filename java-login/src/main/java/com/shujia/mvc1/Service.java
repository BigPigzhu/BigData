package com.shujia.mvc1;


/**
 * 业务层： 编写约为代码
 */

public class Service {

    private Dao dao = new Dao();

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

        return "注册成功";
    }

}
