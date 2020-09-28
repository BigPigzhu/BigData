package com.shujia.service;

import com.shujia.bean.Student;
import com.shujia.bean.SumScore;

public interface CacheService {

    Student queryStudentBuId(String id);

    SumScore querySumScoreBuId(String id);
}
