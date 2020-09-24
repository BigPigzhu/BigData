package com.shujia.mvc2;


import com.shujia.mvc2.service.Service;

/**
 * 控制层   ； 和用户交互
 *
 */
public class Controller {

    //  多态   父类的引用指向子类的对象
    private Service service ;

    public Controller(Service service) {
        this.service = service;
    }

    public String login(String username, String password){

        //调度业务层登录的方法
        String login = service.login(username, password);
        return login;
    }

    public String register(String username,String password,String newpassword){

        //调用业务层注册的方法
        String register = service.register(username, password, newpassword);

        return register;
    }

}
