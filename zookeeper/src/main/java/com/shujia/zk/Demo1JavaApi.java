package com.shujia.zk;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class Demo1JavaApi {

    public static void main(String[] args) throws Exception {


        //1、创建链接
        ZooKeeper zk = new ZooKeeper("master:2181,node1:2181,node2:2181", 3000, null);


        //2、获取数据
        byte[] tests = zk.getData("/test", null, null);

        String s = new String(tests);
        System.out.println(s);


        //3、关闭链接
        zk.close();

    }
}
