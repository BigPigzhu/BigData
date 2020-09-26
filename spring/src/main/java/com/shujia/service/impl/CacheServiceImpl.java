package com.shujia.service.impl;

import com.shujia.bean.Student;
import com.shujia.dao.CacheDao;
import com.shujia.dao.impl.RedisDao;
import com.shujia.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private CacheDao cacheDao;


    @Autowired
    private RedisDao redisDao;

    @Override
    public Student queryStudentBuId(String id) {
        /**
         * 使用缓存
         *
         * 1、先去redis中查询，如果redis中有就直接返回，如果没有再查询mysql
         *
         *
         */

        String key = "cache:" + id;
        Student student1 = redisDao.queryByKey(key);

        //如果redis中有，直接返回数据
        if (student1 != null) {

            /**
             * 每一次被查询，重置过期时间
             *
             */
            redisDao.expire(key, 10);

            return student1;
        }

        //如果没有再查询数据库
        Student student = cacheDao.queryStudentBuId(id);

        //再将查询回来的结果保持到redis中
        redisDao.saveCache(key, student);
        //增加过期时间

        redisDao.expire(key, 10);

        return student;

    }
}
