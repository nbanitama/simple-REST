package com.example.demo.controller.impl;

import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.response.Error;
import com.example.demo.util.response.ErrorStatus;
import com.example.demo.util.response.Response;
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
    public ResponseEntity listUsersIndex() {
        return this.getListUser();
    }

    private ResponseEntity getListUser() {
        try {
            List<User> users = this.userService.findAll();
            if(users.isEmpty())
                return new ResponseEntity<Object>(new Response<>(ErrorStatus.NO_DATA), HttpStatus.OK);
            return new ResponseEntity<Object>(new Response<>(users), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 422, message = "Unprocessable entity found", response = HttpHeaders.class)
    })
    public ResponseEntity listAllUsers() {
        return this.getListUser();
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
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully create user", response = Error.class),
            @ApiResponse(code = 422, message = "Unprocessable entity found", response = HttpHeaders.class)
    })
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        try {
            LOGGER.info("creating user {}", user);
            User dbUser = this.userService.findOne(user.getName());
            if(dbUser != null){
                LOGGER.error("Unable to create. A user with name {} already exists", user.getName());
                return new ResponseEntity<Object>(new Response<>(ErrorStatus.DUPLICATE), HttpStatus.OK);
            }
            user = this.userService.save(user);
            return new ResponseEntity<Object>(new Response<>(ErrorStatus.CREATED, user), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update data", response = User.class),
            @ApiResponse(code = 422, message = "Unprocessable entity found", response = HttpHeaders.class)
    })
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        try {
            LOGGER.info("updating user with id {}", id);

            User currentUser = this.userService.findById((int)id);
            if(currentUser==null){
                LOGGER.error("Unable to update. User with id {} is not found", id);
                return new ResponseEntity<Object>(new Response<>(ErrorStatus.NOT_FOUND), HttpStatus.OK);
            }
            currentUser.setName(user.getName());
            currentUser.setAge(user.getAge());
            currentUser.setSalary(user.getSalary());
            currentUser = this.userService.save(currentUser);
            return new ResponseEntity<Object>(new Response<>(ErrorStatus.UPDATED, currentUser), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update data", response = User.class),
            @ApiResponse(code = 422, message = "Unprocessable entity found", response = HttpHeaders.class)
    })
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        try {
            LOGGER.info("fetching and deleting user with id {}", id);

            User user = this.userService.findById((int)id);
            if(user==null){
                LOGGER.error("Unable to delete. User with id {} is not found", id);
                return new ResponseEntity<Object>(new Response<>(ErrorStatus.NOT_FOUND), HttpStatus.OK);
            }
            this.userService.deleteById((int)id);
            return new ResponseEntity<>(new Response<>(ErrorStatus.DELETED), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update data", response = User.class),
            @ApiResponse(code = 422, message = "Unprocessable entity found", response = HttpHeaders.class)
    })
    public ResponseEntity<?> deleteAllUsers() {
        try {
            LOGGER.info("deleting all users");
            this.userService.deleteAll();
            return new ResponseEntity<>(new Response<>(ErrorStatus.DELETED), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
