package com.example.carcassinternapp.data.repository;

import com.example.carcassinternapp.data.model.TextData;
import org.springframework.data.repository.CrudRepository;

public interface TextRepository extends CrudRepository<TextData, Long> {
}