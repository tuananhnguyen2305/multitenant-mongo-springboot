package com.example.mongotest.repository;

import com.example.mongotest.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends MongoRepository<User, String> {
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
}
