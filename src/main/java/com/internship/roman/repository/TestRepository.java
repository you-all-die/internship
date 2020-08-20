package com.internship.roman.repository;

import com.internship.roman.entity.TestValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestValue,Long> {
    List<TestValue> findAllByOrderByIdDesc();
}
