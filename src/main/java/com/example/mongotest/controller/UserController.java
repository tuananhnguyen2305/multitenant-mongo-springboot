package com.example.mongotest.controller;

import com.example.mongotest.configuration.MongoConnectionManager;
import com.example.mongotest.entity.User;
import com.example.mongotest.payload.ResponseData;
import com.example.mongotest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader("X-TenantId") String databaseName) {
        MongoConnectionManager.setDatabase(databaseName);
        ResponseData responseData = new ResponseData();
        try {
            responseData.setData(userService.getAllUsers());
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } catch (Exception e) {
            responseData.setData("Get all user fail");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getUserById(@RequestHeader("X-TenantId") String databaseName,
                                         @PathVariable String id) {
        MongoConnectionManager.setDatabase(databaseName);
        ResponseData responseData = new ResponseData();
        if (userService.getUserById(id) != null) {
            responseData.setData(userService.getUserById(id));
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setData("Get user fail");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<?> getUserByEmail(@RequestHeader("X-TenantId") String databaseName,
                                         @PathVariable String email) {
        MongoConnectionManager.setDatabase(databaseName);
        ResponseData responseData = new ResponseData();

        if (userService.getUserByEmail(email) != null) {
            responseData.setData(userService.getUserByEmail(email));
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setData("Get user fail");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }


        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }

    @DeleteMapping("/delete-by-email/{email}")
    public ResponseEntity<?> deleteUserByEmail(@RequestHeader("X-TenantId") String databaseName,
                                            @PathVariable String email) {
        MongoConnectionManager.setDatabase(databaseName);
        ResponseData responseData = new ResponseData();
        User user = userService.deleteUserByEmail(email);
        if (user != null) {
            responseData.setData(user);
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setData("Delete user fail");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteUserByid(@RequestHeader("X-TenantId") String databaseName,
                                               @PathVariable String id) {
        MongoConnectionManager.setDatabase(databaseName);
        ResponseData responseData = new ResponseData();
        User user = userService.deleteUserById(id);
        if (user != null) {
            responseData.setData(user);
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setData("Delete user fail");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }
}
