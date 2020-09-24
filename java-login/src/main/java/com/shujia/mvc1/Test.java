package com.shujia.mvc1;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {


        //用户和控制层交互
        Controller controller = new Controller();

        //登录
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入用户名：");

        //用户名
        String username = scanner.next();

        System.out.println("请输入用密码：");

        //密码
        String password = scanner.next();


        //登录
        String login = controller.login(username, password);
        System.out.println(login);


    }
}
