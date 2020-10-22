package com.shujia.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.BufferedReader;
import java.io.FileReader;

public class Demo3DianXin {

    public static void main(String[] args) throws Exception {

        /**
         * 以手机号作为rowkey,时间作为版本号，经纬度作为列
         *
         * create 'dianxin',{NAME => 'info', VERSIONS => 60000}
         *
         */
        //1、创建链接,
        //指定链接地址,

        Configuration configuration = new Configuration();

        //指定zk的链接地址
        configuration.set("hbase.zookeeper.quorum", "master,node1,node2");

        Connection connection = ConnectionFactory.createConnection(configuration);

        //获取表对象
        Table table = connection.getTable(TableName.valueOf("dianxin"));


        FileReader fileReader = new FileReader("C:\\Users\\qx\\Desktop\\DIANXIN.csv");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;

        while ((line = bufferedReader.readLine()) != null) {

            String[] split = line.split(",");
            String mdn = split[0];//手机号

            String date = split[1];//时间

            //以时间作为版本号
            long ts = Long.parseLong(date);

            String x = split[4];//经度
            String y = split[5];//纬度

            String xy = x + "," + y;

            Put put = new Put(mdn.getBytes());
            put.addColumn("info".getBytes(), "xy".getBytes(), ts, xy.getBytes());


            table.put(put);
        }

        System.out.println("数据保存成功");


    }

}
