package com.shujia.redis;

import redis.clients.jedis.Jedis;

public class Demo1RedisJavaApi {
    public static void main(String[] args) {

        /*
         * 创建链接
         *
         */

        Jedis jedis = new Jedis("master", 6379);

        //查询数据
        String a = jedis.get("a");
        System.out.println(a);

        jedis.set("a", "java");

        //设置过期i时间
        jedis.expire("a", 10);

        Long ttl = jedis.ttl("a");

        System.out.println(ttl);


        //关闭连接
        jedis.close();
    }
}
