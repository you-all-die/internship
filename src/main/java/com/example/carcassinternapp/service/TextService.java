package com.example.carcassinternapp.service;

import com.example.carcassinternapp.data.model.TextData;
import com.example.carcassinternapp.data.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextService {

    @Autowired
    private TextRepository textRepository;

    public final Iterable<TextData> findall(){
        return textRepository.findAll();
    }

    public void delete(long id){
        textRepository.deleteById(id);
    }

    public void insert(String value){
        TextData textData = new TextData();
        textData.setValue(value);
        textRepository.save(textData);
    }

    public void deleteAll(){
        textRepository.deleteAll();
    }
}
