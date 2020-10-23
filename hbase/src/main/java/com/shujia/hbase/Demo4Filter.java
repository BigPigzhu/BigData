package com.shujia.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Demo4Filter {


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

    /**
     * 取出文科学生
     * <p>
     * 1、不使用过滤器，在客户端对数据进行过滤
     * 在客户端对数据进行过滤，会导致网卡流量剧增，效率较低
     */
    @Test
    public void scan() throws Exception {
        Table table = connection.getTable(TableName.valueOf("student"));

        Scan scan = new Scan();

        ResultScanner scanner = table.getScanner(scan);

        Result reult;

        while ((reult = scanner.next()) != null) {
            String clazz = Bytes.toString(reult.getValue("info".getBytes(), "clazz".getBytes()));

            if (clazz.startsWith("文科")) {

                String id = Bytes.toString(reult.getRow());

                String name = Bytes.toString(reult.getValue("info".getBytes(), "name".getBytes()));
                String age = Bytes.toString(reult.getValue("info".getBytes(), "age".getBytes()));
                String gender = Bytes.toString(reult.getValue("info".getBytes(), "gender".getBytes()));

                System.out.println(id + "\t" + name + "\t" + age + "\t" + gender + "\t" + clazz);
            }

        }
    }


    /**
     * hbbase 过滤器，
     * 运行在regionserver中的一个过滤器， 返回给客户端的数据是过滤之后的数据
     */

    @Test
    public void scanFilter() throws Exception {
        Table student = connection.getTable(TableName.valueOf("student"));

        Scan scan = new Scan();

        // 正则比较器
        //RegexStringComparator comparator = new RegexStringComparator("文科*");

        //字符串包含比较器
        //SubstringComparator substringComparator = new SubstringComparator("文科");


        //二进制前缀比较器
        //BinaryPrefixComparator binaryPrefixComparator = new BinaryPrefixComparator("文科".getBytes());


        //二进制比较器
        BinaryComparator binaryComparator = new BinaryComparator("文科一班".getBytes());


        /**
         * 列值过滤器需要做全表扫描，效率低
         *
         */

        //创建列值过滤器
        SingleColumnValueFilter valueFilter = new SingleColumnValueFilter(
                "info".getBytes(), //列簇
                "clazz".getBytes(),// 列名
                CompareFilter.CompareOp.EQUAL, //比较规则
                binaryComparator// 比较器
        );


        //增加过滤器， 运行在服务端
        scan.setFilter(valueFilter);


        //执行扫描
        ResultScanner scanner = student.getScanner(scan);


        Result reult;

        while ((reult = scanner.next()) != null) {
            String clazz = Bytes.toString(reult.getValue("info".getBytes(), "clazz".getBytes()));
            String id = Bytes.toString(reult.getRow());
            String name = Bytes.toString(reult.getValue("info".getBytes(), "name".getBytes()));
            String age = Bytes.toString(reult.getValue("info".getBytes(), "age".getBytes()));
            String gender = Bytes.toString(reult.getValue("info".getBytes(), "gender".getBytes()));

            System.out.println(id + "\t" + name + "\t" + age + "\t" + gender + "\t" + clazz);


        }


    }


}
