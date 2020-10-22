package com.shujia.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

public class Demo1Api {

    public static void main(String[] args) throws Exception {


        /**
         * 通过java链接hbase
         *
         * 执行java代码的节点必须配置整个hbase集群的hosts
         *
         */

        //1、创建链接,
        //指定链接地址,

        Configuration configuration = new Configuration();

        //指定zk的链接地址
        configuration.set("hbase.zookeeper.quorum", "master,node1,node2");

        Connection connection = ConnectionFactory.createConnection(configuration);

        //获取表对象
        Table table = connection.getTable(TableName.valueOf("shujia:User"));

        //查询数据, 通过rowkey查询数据
        Get get = new Get("row1".getBytes());
        Result result = table.get(get);

        //解析result
        byte[] value = result.getValue("info".getBytes(), "name".getBytes());

        String s = new String(value);

        System.out.println(s);


        //关闭链接
        connection.close();

    }
}
