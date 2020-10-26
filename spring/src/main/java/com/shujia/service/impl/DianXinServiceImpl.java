package com.shujia.service.impl;

import com.shujia.bean.Location;
import com.shujia.dao.DianXInDao;
import com.shujia.service.DianXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DianXinServiceImpl implements DianXinService {


    @Autowired
    private DianXInDao dianXInDao;


    @Override
    public List<Location> queryLocationByMdn(String mdn, int limit) {
        return dianXInDao.queryLocationByMdn(mdn, limit);
    }

    @Override
    public List<Location> queryLocationByMdnAndCityId(String mdn, String city) {
        return dianXInDao.queryLocationByMdnAndCityId(mdn, city);
    }
}
