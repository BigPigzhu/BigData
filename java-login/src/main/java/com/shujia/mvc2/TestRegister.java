package com.shujia.mvc2;

import com.shujia.mvc2.dao.DaoMysql;
import com.shujia.mvc2.service.ServiceImpl;

import java.util.Scanner;

public class TestRegister {
    public static void main(String[] args) {

        DaoMysql daoMysql = new DaoMysql();

        ServiceImpl service = new ServiceImpl(daoMysql);

        //用户和控制层交互
        Controller controller = new Controller(service);

        //登录
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入用户名：");

        //用户名
        String username = scanner.next();

        System.out.println("请输入用密码：");

        //密码
        String password = scanner.next();

        System.out.println("确认密码：");

        //密码
        String newpassword = scanner.next();

        String register = controller.register(username, password, newpassword);

        System.out.println(register);


    }
}
