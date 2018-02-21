package com.example.demo.controller.impl;

import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Error;
import com.example.demo.util.ErrorStatus;
import com.example.demo.util.Response;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noba on 12/16/2017.
 */
@RestController
public class UserControllerImpl implements UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerImpl.class);

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 422, message = "Unprocessable entity found", response = HttpHeaders.class)
    })
    public ResponseEntity<List<User>> listAllUsers() {
        try {
            List<User> users = this.userService.findAll();
            if(users.isEmpty())
                return new ResponseEntity(new Response<>(ErrorStatus.NO_DATA), HttpStatus.OK);
            //return new ResponseEntity(HttpStatus.NO_CONTENT);
            return new ResponseEntity(new Response<>(users), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = User.class),
            @ApiResponse(code = 422, message = "Unprocessable entity found", response = HttpHeaders.class)
    })
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        try {
            LOGGER.info("Fetching user with id {}", id);
            User user = this.userService.findById((int)id);
            if(user == null)
                return new ResponseEntity<Object>(new Response<>(ErrorStatus.NOT_FOUND), HttpStatus.OK);
            return new ResponseEntity<Object>(new Response<>(user), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<Object>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Failed to create user", response = Error.class),
            @ApiResponse(code = 201, message = "Successfully create user", response = HttpHeaders.class)
    })
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        LOGGER.info("creating user {}", user);
        User dbUser = this.userService.findOne(user.getName());
        if(dbUser != null){
            LOGGER.error("Unable to create. A user with name {} already exists", user.getName());
            return new ResponseEntity(new Error("Unable to create. A user with name " + user.getName() + " already exists"), HttpStatus.OK);
        }
        this.userService.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = User.class),
            @ApiResponse(code = 404, message = "No data is found-1", response = Error.class)
    })
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        LOGGER.info("updating user with id {}", id);

        User currentUser = this.userService.findById((int)id);
        if(currentUser==null){
            LOGGER.error("Unable to update. User with id {} is not found", id);
            return new ResponseEntity(new Error("Unable to update. User with id "+id+" is not found"), HttpStatus.NOT_FOUND);
        }
        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());
        this.userService.save(currentUser);
        return new ResponseEntity<Object>(currentUser, HttpStatus.OK);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "No data is found-1", response = Error.class),
            @ApiResponse(code = 204, message = "No data is found", response = User.class)
    })
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        LOGGER.info("fetching and deleting user with id {}", id);

        User user = this.userService.findById((int)id);
        if(user==null){
            LOGGER.error("Unable to delete. User with id {} is not found", id);
            return new ResponseEntity(new Error("Unable to delete. User with id "+id+" is not found"), HttpStatus.NOT_FOUND);
        }
        this.userService.deleteById((int)id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No data is found")
    })
    public ResponseEntity<User> deleteAllUsers() {
        LOGGER.info("deleting all users");
        this.userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
