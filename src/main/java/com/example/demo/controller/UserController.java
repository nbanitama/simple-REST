package com.example.demo.controller;

import com.example.demo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by noba on 12/16/2017.
 */
@Api(value = "user api", description = "API relates to User Data")
public interface UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "view a list of user data")
    ResponseEntity listUsersIndex();

    @RequestMapping(value = "/user/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "view a list of user data")
    ResponseEntity listAllUsers();

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get a user data")
    ResponseEntity<?> getUser(long id);

    @RequestMapping(value = "/user/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create a user data")
    ResponseEntity<?> createUser(User user, UriComponentsBuilder ucBuilder);

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update a user data")
    ResponseEntity<?> updateUser(long id,  User user);

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete a user data")
    ResponseEntity<?> deleteUser( long id);

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete all user data")
    ResponseEntity<?> deleteAllUsers();
}
