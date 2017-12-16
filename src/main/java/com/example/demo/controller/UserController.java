package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by noba on 12/16/2017.
 */
@RequestMapping(value = "/user")
public interface UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<User>> listAllUsers();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getUser(long id);

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<?> createUser(User user, UriComponentsBuilder ucBuilder);

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateUser(long id,  User user);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteUser( long id);

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    ResponseEntity<User> deleteAllUsers();
}
