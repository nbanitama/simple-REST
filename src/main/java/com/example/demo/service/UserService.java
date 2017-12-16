package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by noba on 12/16/2017.
 */
public interface UserService {

    List<User> findAll();
    User findById(Integer id);
    @Transactional
    void save(User user);
    @Transactional
    void deleteById(Integer id);
    @Transactional
    void deleteAll();
    User findOne(String name);
}
