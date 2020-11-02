package com.shujia.spark.core.task;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Driver {

    public static void main(String[] args) throws Exception {

        /**
         * 线程对象: 通过task创建的对象
         *
         */

        Task task = new Task();


        //在本地启动线程
//        task.start();


        //启动客户端连接服务端
        Socket socket = new Socket("localhost", 9999);


        //获取输出流
        OutputStream outputStream = socket.getOutputStream();

        //将字节流转换成对象流
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // 发送task 到executor中

        objectOutputStream.writeObject(task);

        objectOutputStream.flush();


        System.out.println("连接成功");


        for (int i = 0; i < 10; i++) {
            System.out.println("main:" + i);
        }


    }
}

/**
 * 如果对象要在网络中传输,或者保存到磁盘,需要可以序列化  实现Serializable接口
 */

class Task extends Thread implements Serializable {
    @Override
    public void run() {
        /**
         * 算子的逻辑
         *
         */

        for (int i = 0; i < 10; i++) {
            System.out.println("task:" + i);
        }

    }
}
