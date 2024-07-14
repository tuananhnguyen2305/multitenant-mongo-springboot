package com.example.mongotest.configuration;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Component;

@Component
public class MongoConnectionManager {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    private static final ThreadLocal<String> threadLocalDb = new ThreadLocal<>();

    public static void setDatabase(String databaseName) {

        threadLocalDb.set(databaseName);

    }

    public static String getDatabase() {
        return threadLocalDb.get();
    }

    public static void clear() {
        threadLocalDb.remove();
    }

    @Bean
    @Lazy
    public MongoTemplate mongoTemplate() {
        String database = MongoConnectionManager.getDatabase();
        if (database != null) {
//            ConnectionString connectionString = new ConnectionString(MongoConnectionManager.getDatabase());
//            return new MongoTemplate(new DatabaseConfig(connectionString));
            return new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), database));

        }

        return new MongoTemplate(MongoClients.create(mongoUri), "centraldb");
    }

}
