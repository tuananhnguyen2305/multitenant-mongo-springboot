package com.example.mongotest.service;

import com.example.mongotest.configuration.MongoConnectionManager;
import com.example.mongotest.entity.User;
import com.example.mongotest.payload.AuthenticationResponse;
import com.example.mongotest.repository.IUserRepo;
import com.example.mongotest.utils.JWTUtilsHelper;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    JWTUtilsHelper jwtUtilsHelper;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Autowired
    MongoTemplate mongoTemplate;


    public UserService(@Lazy MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User save(User user) {
        user.setTenantId(MongoConnectionManager.getDatabase());
        mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), MongoConnectionManager.getDatabase()));
        return mongoTemplate.save(user);
    }

    public AuthenticationResponse checkLogin(String databaseName ,String email, String password) {
        try {
            mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), MongoConnectionManager.getDatabase()));
            Query query = new Query();
            query.addCriteria(Criteria
                    .where("email").is(email)
                    .and("password").is(password));

            User user = mongoTemplate.findOne(query, User.class);
            if (user != null) {
                String token = jwtUtilsHelper.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(token)
                        .authenticated(true)
                        .data(user)
                        .build();
            }
        } catch (Exception e) {
            System.out.println("Error in check login: " + e.getMessage());
        }

        return AuthenticationResponse.builder()
                .token(null)
                .authenticated(false)
                .build();
    }

    public List<User> getAllUsers() {
        mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), MongoConnectionManager.getDatabase()));
        return mongoTemplate.findAll(User.class);
    }

    public User getUserById(String id) {
        mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), MongoConnectionManager.getDatabase()));
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, User.class);
    }

    public User getUserByEmail(String email) {
        mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), MongoConnectionManager.getDatabase()));
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class);
    }

    public User deleteUserById(String id) {
        mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), MongoConnectionManager.getDatabase()));

        User user = getUserById(id);
        if (user != null) {
            mongoTemplate.remove(user);
            return user;
        }
        return null;
    }

    public User deleteUserByEmail(String email) {
        mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), MongoConnectionManager.getDatabase()));

        User user = getUserByEmail(email);
        if (user != null) {
            mongoTemplate.remove(user);
            return user;
        }
        return null;
    }
}
