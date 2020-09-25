package com.shujia.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * spring boot在启动的过程中会扫描Application 下面所在的类，获取类上的注解，
 * 如果类上有RestController spring boot 会通过反射创建这个类的对象
 * 再扫描所有的方法，如果方法是有GetMapping  注解会将这个方法绑定到资源访问路径上（http://localhost:8080/test）
 *
 *
 * spring boot 底层是tcp/ip    封装了http，  内部使用了大量的反射
 *
 */

@RestController
public class TestController {

    static {
        System.out.println("TestController被加载了");
    }


    public TestController() {
        System.out.println("TestController被初始化了");
    }

    /**
     * http://localhost:8080/test?username=zhangsan&password=123
     *
     */
    @GetMapping("/test")
    public String test(String username, String password){
        System.out.println(username+"\t"+password);


        //返回给前端
        return "登录成功";
    }
}
