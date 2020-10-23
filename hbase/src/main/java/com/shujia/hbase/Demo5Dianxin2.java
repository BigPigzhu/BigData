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

public class Demo5Dianxin2 {


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
    public void putdianxin() throws Exception {
        /**
         * 手机号和时间一起作为rowkey
         *
         * 换的时候使用rowkey 前缀过滤  ，效率也比较高
         *
         * 怎么清理过期数据
         *
         * 通过ttl 清理,
         *
         */

        // create 'dianxin1',{NAME => 'info', TTL => 60000}


        Configuration configuration = new Configuration();

        //指定zk的链接地址
        configuration.set("hbase.zookeeper.quorum", "master,node1,node2");

        Connection connection = ConnectionFactory.createConnection(configuration);

        //获取表对象
        Table table = connection.getTable(TableName.valueOf("dianxin1"));


        FileReader fileReader = new FileReader("C:\\Users\\qx\\Desktop\\DIANXIN.csv");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;

        while ((line = bufferedReader.readLine()) != null) {

            String[] split = line.split(",");
            String mdn = split[0];//手机号

            String date = split[1];//时间

            //大数减小数，实现降序，将新的数据放前面
            long l = 40000000000000L - Long.parseLong(date);

            String rowkey = mdn + "_" + l;

            String x = split[4];//经度
            String y = split[5];//纬度

            String xy = x + "," + y;

            Put put = new Put(rowkey.getBytes());
            put.addColumn("info".getBytes(), "xy".getBytes(), xy.getBytes());

            table.put(put);
        }

        System.out.println("数据保存成功");


    }

    /**
     * 通过过滤器查询用户最新的数据
     */

    @Test
    public void scanFIlter() throws Exception {

        String mdn = "47BE1E866CFC071DB19D5E1C056BE28AE24C16E7";

        Table dianxin1 = connection.getTable(TableName.valueOf("dianxin1"));


        //二进制前缀比较器
        BinaryPrefixComparator binaryPrefixComparator = new BinaryPrefixComparator(mdn.getBytes());


        //创建行键过滤器    不需要做全表扫描
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, binaryPrefixComparator);


        Scan scan = new Scan();

        scan.setFilter(rowFilter);


        //获取最新数据
        scan.setLimit(10);

        ResultScanner scanner = dianxin1.getScanner(scan);


        Result result;

        while ((result = scanner.next()) != null) {

            String rowkey = Bytes.toString(result.getRow());

            //获取时间
            String[] split = rowkey.split("_");

            String l = split[1];

            //获取时间
            long date = 40000000000000L - Long.parseLong(l);


            String xy = Bytes.toString(result.getValue("info".getBytes(), "xy".getBytes()));

            System.out.println(date + '\t' + xy);


        }


    }


}
