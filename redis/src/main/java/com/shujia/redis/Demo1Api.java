package com.shujia.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.io.BufferedReader;
import java.io.FileReader;

public class Demo1Api {

    private Jedis jedis;

    @Before
    public void init() {
        //创建链接
        jedis = new Jedis("master", 6379);
    }

    @Test
    public void set() {

        //插入一条数据
        jedis.set("key", "java");

        //参数对象
        SetParams setParams = SetParams.setParams();

        //过期时间
        setParams.ex(10);

        //带参数set
        jedis.set("key1", "value1", setParams);
    }


    /**
     * 将学生信息保持到reids中，一学号选座位key   学生信息作为value
     */

    @Test
    public void setStudent() throws Exception {

        //读取学生表

        FileReader fileReader = new FileReader("data/students.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        while ((line = bufferedReader.readLine()) != null) {

            //将数据保持到redis中
            String id = line.split(",")[0];

            /**
             * 为了查看起来方便，最好在key前面加上一个前缀
             *
             * 一般使用表名作为前缀，主要做到见名知意
             */

            String key = "student:" + id;

            jedis.set(key, line);

        }

    }

    @Test
    public void get() {

        String s = jedis.get("student:1500100801");

        System.out.println(s);

    }


    @After
    public void close() {
        jedis.close();
    }

}
