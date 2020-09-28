package com.shujia.dao;

import com.shujia.bean.Student;
import com.shujia.bean.SumScore;

public interface CacheDao {
    Student queryStudentBuId(String id);

    SumScore querySumSocreById(String id);
}
