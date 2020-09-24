package com.shujia.mvc1;


/**
 * 控制层   ； 和用户交互
 *
 */
public class Controller {

    private Service service = new Service();

    public String login(String username,String password){

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
