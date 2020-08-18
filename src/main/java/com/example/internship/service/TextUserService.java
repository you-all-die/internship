package com.example.internship.service;

import com.example.internship.model.TextUser;
import com.example.internship.repository.TextUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TextUserService {

    @Autowired
    TextUserRepository repo;

    public void save(TextUser textUser) {
        repo.save(textUser);
    }

    public List<TextUser> listAll() {
        return (List<TextUser>) repo.findAll();
    }

    public TextUser get (Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
