package com.shujia.spark.core.task;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Executor {

    public static void main(String[] args) throws Exception {


        /**
         *
         * scoket编程
         *
         */

        // 创建服务端
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务端启动成功");
        //等待请求
        Socket socket = serverSocket.accept();
        System.out.println("收到请求");

        //获取输入流
        InputStream inputStream = socket.getInputStream();

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        //接受task
        Thread object = (Thread) objectInputStream.readObject();


        //在Executor中通过线程池启动task

        object.start();



    }
}
