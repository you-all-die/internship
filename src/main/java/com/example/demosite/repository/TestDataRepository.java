package com.example.demosite.repository;

import com.example.demosite.model.TestData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDataRepository extends JpaRepository<TestData, Long>{
}
