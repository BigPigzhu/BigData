package com.shujia.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Demo6Student {


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
    public void saveData() throws Exception {
        /**
         * 根据班级查询班级所有学生，返回学生信息
         *
         * 1、以学号作为key,班级作为列
         *      查询的时候使用列值过滤器，效率较低
         * 2、以班级+学号作为key
         *     rowkey前缀过滤器，不需要做全表扫描
         *
         *     二级索引 --> 建立行键与列值的映射关系
         *
         *
         * 创建表
         *  create 'student_clazz','i'
         *
         */


        Table table = connection.getTable(TableName.valueOf("student_clazz"));


        FileReader fileReader = new FileReader("data/students.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;

        while ((line = bufferedReader.readLine()) != null) {

            String[] split = line.split(",");
            String id = split[0];
            String name = split[1];
            String age = split[2];
            String gender = split[3];
            String clazz = split[4];

            //构建rowkey
            String rowkey = clazz + "_" + id;


            Put put = new Put(rowkey.getBytes());

            put.addColumn("i".getBytes(), "name".getBytes(), name.getBytes());
            put.addColumn("i".getBytes(), "age".getBytes(), age.getBytes());
            put.addColumn("i".getBytes(), "gender".getBytes(), gender.getBytes());

            table.put(put);

        }

    }

    @Test
    public void queryData() throws Exception {

        /**
         * 查询文科一班所有学生
         *
         */

        Table table = connection.getTable(TableName.valueOf("student_clazz"));


        //二进制前缀比较器
        BinaryPrefixComparator binaryPrefixComparator = new BinaryPrefixComparator("文科一班".getBytes());


        //rowkey过滤器
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, binaryPrefixComparator);


        Scan scan = new Scan();

        //增加过滤器
        scan.setFilter(rowFilter);

        ResultScanner scanner = table.getScanner(scan);


        Result result;


        while ((result = scanner.next()) != null) {
            String name = Bytes.toString(result.getValue("i".getBytes(), "name".getBytes()));
            String age = Bytes.toString(result.getValue("i".getBytes(), "age".getBytes()));
            String gender = Bytes.toString(result.getValue("i".getBytes(), "gender".getBytes()));

            String[] split = Bytes.toString(result.getRow()).split("_");
            String clazz = split[0];
            String id = split[1];


            System.out.println(id + "\t" + name + "\t" + age + "\t" + gender + "\t" + clazz);
        }

    }


}
