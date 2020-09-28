package com.shujia.dao.impl;

import com.shujia.bean.Student;
import com.shujia.bean.SumScore;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisDao {

    private static Jedis jedis;

    static {

        //奖励redis链接
        jedis = new Jedis("master", 6379);
    }

    /**
     * 通过key查询value
     *
     * @param key
     * @return
     */

    public Student queryByKey(String key) {
        //如果key不存在返回null
        String str = jedis.get(key);

        if (str == null) {
            return null;
        }


        String[] split = str.split(",");

        Student student = new Student(split[0], split[1], Integer.parseInt(split[2]), split[3], split[4]);

        return student;
    }

    /**
     * 通过学号查询学生总分
     */

    public Integer querySumScoreByKey(String key) {
        String s = jedis.get(key);

        if (s == null) {
            return null;
        }

        int sumScore = Integer.parseInt(s);

        return sumScore;

    }


    /**
     * 保存一条数据
     */


    public void saveCache(String key, Student student) {

        String line = student.getId() + "," + student.getName() + "," + student.getAge() + "," + student.getGender() + "," + student.getClazz();

        jedis.set(key, line);
    }

    public void saveSumScore(String key, Integer sumScore) {

        jedis.set(key, sumScore.toString());
    }


    /**
     * 增加过期时间
     */

    public void expire(String key, int ex) {

        jedis.expire(key, ex);
    }
}
