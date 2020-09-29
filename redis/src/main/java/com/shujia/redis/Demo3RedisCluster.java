package com.shujia.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;

public class Demo3RedisCluster {
    public static void main(String[] args) {


        //节点列表
        HashSet<HostAndPort> nodes = new HashSet<HostAndPort>();

        nodes.add(new HostAndPort("master", 7000));
        nodes.add(new HostAndPort("master", 7001));
        nodes.add(new HostAndPort("node1", 7002));
        nodes.add(new HostAndPort("node1", 7003));
        nodes.add(new HostAndPort("node2", 7004));
        nodes.add(new HostAndPort("node2", 7005));


        /**
         * 加入连接池
         *
         */

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(2);

        /*
         * 链接redis集群
         *
         */
        JedisCluster cluster = new JedisCluster(nodes, poolConfig);

        cluster.set("java", "java");

        System.out.println(cluster.get("java"));

        //关闭链接
        cluster.close();


    }
}
