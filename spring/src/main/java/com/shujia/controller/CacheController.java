package com.shujia.controller;


import com.shujia.bean.Student;
import com.shujia.bean.SumScore;
import com.shujia.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;


    /**
     * 通过学号查询学生信息
     */

    @GetMapping("/queryStudent")
    public Student queryStudentBuId(String id) {

        Student student = cacheService.queryStudentBuId(id);

        return student;

    }

    /**
     * 通过学号查询学生总分
     *
     */

    @GetMapping("/querySumScoreById")
    public SumScore querySumScoreById(String id) {

        SumScore sumScore = cacheService.querySumScoreBuId(id);

        return sumScore;

    }

}
