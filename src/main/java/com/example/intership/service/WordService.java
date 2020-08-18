package com.example.intership.service;

import com.example.intership.data.entity.Word;
import com.example.intership.data.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    public final Iterable<Word> findAll() {
        return wordRepository.findAll();
    }

    public void insert(String value) {
        Word word = new Word();
        word.setValue(value);
        wordRepository.save(word);
    }

    public void delete(long id) {
        wordRepository.deleteById(id);
    }

    public void deleteAll() {
        wordRepository.deleteAll();
    }
}
