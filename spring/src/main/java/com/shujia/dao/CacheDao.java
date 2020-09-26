package com.shujia.dao;

import com.shujia.bean.Student;

public interface CacheDao {
    Student queryStudentBuId(String id);
}
