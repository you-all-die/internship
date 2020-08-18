package com.example.intership.data.repository;

import com.example.intership.data.entity.Word;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<Word, Long> {
}
