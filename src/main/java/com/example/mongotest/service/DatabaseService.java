package com.example.mongotest.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    public MongoDatabase createDb(String name, String url) {
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase(name);
        database.createCollection("user");
        return database;
    }
}
