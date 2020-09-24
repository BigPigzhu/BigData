package com.shujia.mvc2;

import com.shujia.mvc2.dao.DaoMysql;
import com.shujia.mvc2.dao.DaoSqlServer;
import com.shujia.mvc2.service.ServiceImpl;

import java.util.Scanner;

public class TestLogin {
    public static void main(String[] args) {


        //数据库持久层对象
        DaoMysql daoMysql = new DaoMysql();

        DaoSqlServer daoSqlServer = new DaoSqlServer();

        //业务层对象
        ServiceImpl service = new ServiceImpl(daoSqlServer);

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


        //登录
        String login = controller.login(username, password);
        System.out.println(login);


    }
}
