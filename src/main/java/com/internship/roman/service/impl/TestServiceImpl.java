package com.internship.roman.service.impl;

import com.internship.roman.entity.TestValue;
import com.internship.roman.repository.TestRepository;
import com.internship.roman.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class TestServiceImpl implements TestService {

    @Autowired
    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }


    @Override
    public TestValue save(String string) {
        TestValue testValue = new TestValue();
        testValue.setText(string);
        testRepository.save(testValue);
        return testValue;
    }

    @Override
    public List<TestValue> getAll() {
        return  testRepository.findAllByOrderByIdDesc();
    }


    @Override
    public Long deleteById(Long id) {
        testRepository.deleteById(id);
        return id;
    }

    @Override
    public void deleteAll() {
    testRepository.deleteAll();
    }

}
