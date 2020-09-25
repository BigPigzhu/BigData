package com.shujia.controller;

import com.shujia.bean.User;
import com.shujia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    /**
     * Autowired  ; 依赖注入
     * spring boot 回去根据类型，自动给属性赋值
     *
     * 底层使用的是反射
     *
     */
    @Autowired
    private UserService userService ;


    /**
     * 登录接口
     *
     * http://192.168.0.197:8080/login?username=zhangsan&password=123
     */


    @GetMapping("/login")
    public String login(String username,String password){

        User user = new User(username, password);

        //调用业务层登录的业务
        String login = userService.login(user);

        return login;
    }

    /**
     * 注册的接口
     *
     *http://192.168.0.197:8080/register?username=zhangsan&password=123&newPassword=123
     */


    @GetMapping("/register")
    public String register(String username,String password,String newPassword){
        //调用业务层登录的业务
        String register = userService.register(username,password,newPassword);

        return register;
    }
}
