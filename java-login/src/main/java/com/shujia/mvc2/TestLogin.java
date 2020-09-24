package com.shujia.mvc2;

import com.shujia.Config;
import com.shujia.mvc2.dao.Dao;
import com.shujia.mvc2.dao.DaoMysql;
import com.shujia.mvc2.dao.DaoSqlServer;
import com.shujia.mvc2.service.Service;
import com.shujia.mvc2.service.ServiceImpl;

import java.lang.reflect.Constructor;
import java.util.Scanner;

public class TestLogin {
    public static void main(String[] args) throws Exception{


        /**
         * 通过反射创建对象
         *
         */

        //获取工具获取dao层的实现类
        String daoCLsss = Config.get("dao.class");

        //加载类
        Class<?> aClass = Class.forName(daoCLsss);

        //通过类对象创建类的对象
        Dao dao = (Dao)aClass.newInstance();


        //加载业务层类
        Class<?> serviceClass = Class.forName(Config.get("service.class"));

        //dnewInstance 调用无参构造函数
        //Service o = (Service)serviceClass.newInstance();

        //获取有参的构造方法
        Constructor<?> constructor = serviceClass.getConstructor(Dao.class);

        //通过有参构造函数创建对象
        Service service = (Service)constructor.newInstance(dao);

        //业务层对象
        //ServiceImpl service = new ServiceImpl(dao);

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
