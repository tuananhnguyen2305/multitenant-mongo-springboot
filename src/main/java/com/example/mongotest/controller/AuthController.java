package com.example.mongotest.controller;

import com.example.mongotest.configuration.MongoConnectionManager;
import com.example.mongotest.entity.User;
import com.example.mongotest.payload.AuthenticationResponse;
import com.example.mongotest.payload.ResponseData;
import com.example.mongotest.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestHeader("X-TenantId") String databaseName,
                                    @RequestBody User user) {
        ResponseData responseData = new ResponseData();
        MongoConnectionManager.setDatabase(databaseName);
        if (databaseName != null) {
            System.out.println(databaseName);

            try {
                responseData.setData(userService.save(user));
                responseData.setSuccess(true);
                responseData.setStatus(200);
            } catch (Exception e) {
                responseData.setData(null);
                responseData.setSuccess(false);
                responseData.setStatus(400);
            }
        }

        MongoConnectionManager.clear();
        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> checkLogin(@RequestHeader("X-TenantId") String databaseName,
                                        @RequestParam String email,
                                        @RequestParam String password) {
        MongoConnectionManager.setDatabase(databaseName);
        AuthenticationResponse authenticationResponse = userService.checkLogin(databaseName, email, password);

        MongoConnectionManager.clear();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
