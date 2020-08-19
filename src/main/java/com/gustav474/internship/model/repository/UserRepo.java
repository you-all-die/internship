package com.gustav474.internship.model.repository;

import com.gustav474.internship.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

}
