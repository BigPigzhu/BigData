package com.shujia.controller;

import com.shujia.bean.Location;
import com.shujia.service.DianXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DianXinController {

    @Autowired
    private DianXinService dianXinService;


    @GetMapping("/queryLocationByMdn")
    public List<Location> queryLocationByMdn(String mdn, int limit) {
        return dianXinService.queryLocationByMdn(mdn, limit);
    }

    @GetMapping("/queryLocationByMdnAndCityId")
    private List<Location> queryLocationByMdnAndCityId(String mdn, String city) {
        return dianXinService.queryLocationByMdnAndCityId(mdn, city);
    }

}
