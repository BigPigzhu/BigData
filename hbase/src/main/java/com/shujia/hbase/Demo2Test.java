package com.shujia.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf("STUDENT"));

        //增加列簇
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("info".getBytes());

        //可以设置列簇的属性
        //hColumnDescriptor.setTimeToLive(5);
        //hColumnDescriptor.setVersions(1, 10);

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

    @Test
    public void put() throws Exception {

        /**
         * 插入数据
         *
         */

        //获取表对象
        Table table = connection.getTable(TableName.valueOf("test2"));


        //创建put对象
        Put put = new Put("001".getBytes());

        //指定列和列值
        put.addColumn("info".getBytes(), "name".getBytes(), "zhangsan".getBytes());
        put.addColumn("info".getBytes(), "age".getBytes(), "23".getBytes());


        //插入数据
        table.put(put);


        //断开和RS的链接
        table.close();

    }


    @Test
    public void putStudent() throws Exception {

        //create 'student','info'
        Table table = connection.getTable(TableName.valueOf("STUDENT"));


        //读取本地学生数据
        FileReader fileReader = new FileReader("data/students.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;

        ArrayList<Put> puts = new ArrayList<Put>();

        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(",");

            String id = split[0];
            String name = split[1];
            String age = split[2];
            String gender = split[3];
            String clazz = split[4];


            /**
             * 以学号作为rowkey
             * 1、唯一
             * 2、后面可以通过学号查询学生信息
             *
             */

            Put put = new Put(id.getBytes());
            put.addColumn("info".getBytes(), "name".getBytes(), name.getBytes());
            put.addColumn("info".getBytes(), "age".getBytes(), age.getBytes());
            put.addColumn("info".getBytes(), "gender".getBytes(), gender.getBytes());
            put.addColumn("info".getBytes(), "clazz".getBytes(), clazz.getBytes());


            puts.add(put);

        }

        //批量插入数据
        table.put(puts);

    }


    @Test
    public void get() throws Exception {
        Table table = connection.getTable(TableName.valueOf("student"));

        Get get = new Get("1500100004".getBytes());
        //指定查询的列，如果不指定，默认返回所有列
        //get.addColumn("info".getBytes(), "name".getBytes());

        Result result = table.get(get);

        //从result中获取rowkey
        String id = Bytes.toString(result.getRow());


        //解析result
        String name = Bytes.toString(result.getValue("info".getBytes(), "name".getBytes()));
        String age = Bytes.toString(result.getValue("info".getBytes(), "age".getBytes()));
        String gender = Bytes.toString(result.getValue("info".getBytes(), "gender".getBytes()));
        String clazz = Bytes.toString(result.getValue("info".getBytes(), "clazz".getBytes()));


        System.out.println(id + "\t" + name + "\t" + age + "\t" + gender + "\t" + clazz);

    }


    @Test
    public void scan() throws Exception {
        Table table = connection.getTable(TableName.valueOf("student"));

        Scan scan = new Scan();

//        scan.setLimit(10);
//        scan.withStartRow("001".getBytes());
//        scan.withStopRow("0010".getBytes());

        //全表扫描
        //ResultScanner 多条数据
        ResultScanner scanner = table.getScanner(scan);


        Result result;

        while ((result = scanner.next()) != null) {


            //从result中获取rowkey
            String id = Bytes.toString(result.getRow());

            //解析result
            String name = Bytes.toString(result.getValue("info".getBytes(), "name".getBytes()));
            String age = Bytes.toString(result.getValue("info".getBytes(), "age".getBytes()));
            String gender = Bytes.toString(result.getValue("info".getBytes(), "gender".getBytes()));
            String clazz = Bytes.toString(result.getValue("info".getBytes(), "clazz".getBytes()));

            System.out.println(id + "\t" + name + "\t" + age + "\t" + gender + "\t" + clazz);

        }

    }


}
