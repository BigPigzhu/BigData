package com.shujia.zk;

import org.apache.zookeeper.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Demo2Test {


    ZooKeeper zk;

    @Before
    public void init() {

        //1、创建链接
        try {
            zk = new ZooKeeper("master:2181,node1:2181,node2:2181", 3000, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建节点
     */

    @Test
    public void create() throws Exception {
        zk.create("/java",
                "java".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL // 临时系欸但那
        );


        while (true) {

        }
    }

    /**
     * 监控节点的状态，如果节点发生改变，执行回调函数
     */

    @Test
    public void watch() throws Exception {

        //Watcher  监控节点，当节点发送改变之后会回调process

        byte[] data = zk.getData("/java", new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent);
                System.out.println("实现namenode的主备切换");
            }
        }, null);

        System.out.println(new String(data));


        while (true) {

        }

    }


    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
