package com.internship.roman.service;

import com.internship.roman.entity.TestValue;

import java.util.List;


public interface TestService {
    TestValue save(String string);
    List<TestValue> getAll();
    Long deleteById(Long id);
    void deleteAll();
}
