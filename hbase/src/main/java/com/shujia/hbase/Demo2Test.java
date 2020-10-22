package com.shujia.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Demo2Test {

    private Connection connection;

    @Before
    public void init() {

        //1、创建链接,
        //指定链接地址,

        Configuration configuration = new Configuration();

        //指定zk的链接地址
        configuration.set("hbase.zookeeper.quorum", "master,node1,node2");

        try {
            connection = ConnectionFactory.createConnection(configuration);

            System.out.println("连接创建成功....");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void createTable() throws IOException {

        //获取管理对象，和hMaster建立链接
        Admin admin = connection.getAdmin();

        //创建表描述对象
        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf("test2"));

        //增加列簇
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("info".getBytes());

        //可以设置列簇的属性
        hColumnDescriptor.setTimeToLive(5);
        hColumnDescriptor.setVersions(1, 10);

        descriptor.addFamily(hColumnDescriptor);

        //创建表
        admin.createTable(descriptor);

    }


    @Test
    public void dropTable() throws Exception {
        //获取管理对象，和hMaster建立链接
        Admin admin = connection.getAdmin();

        //禁用表
        admin.disableTable(TableName.valueOf("test2"));

        //删除表
        admin.deleteTable(TableName.valueOf("test2"));

    }


}
