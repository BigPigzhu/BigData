package com.shujia.TestPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestRedisPool {
    public static void main(String[] args) {

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大连接数
        poolConfig.setMaxIdle(10);


        //创建redis连接池
        JedisPool jedisPool = new JedisPool(poolConfig, "master", 6379);

        //获取李连杰
        Jedis jedis = jedisPool.getResource();

        String a = jedis.get("a");
        System.out.println(a);

        jedis.close();

    }
}
