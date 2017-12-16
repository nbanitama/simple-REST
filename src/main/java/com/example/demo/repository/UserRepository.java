package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by noba on 12/16/2017.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
}
