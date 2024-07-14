package com.example.mongotest.service;

import com.example.mongotest.configuration.MongoConnectionManager;
import com.example.mongotest.entity.Tenant;
import com.example.mongotest.repository.ITenantRepo;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    @Autowired
    ITenantRepo tenantRepo;

    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    public TenantService(@Lazy MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public Tenant save(Tenant tenant) {
        return tenantRepo.save(tenant);
    }

    public List<Tenant> getAll() {
        //mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), MongoConnectionManager.getDatabase()));
        return mongoTemplate.findAll(Tenant.class);
    }

    public Tenant getTenantById(String id) {

        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Tenant.class);
    }

    public Tenant deleteTenantById(String id) {
        Tenant tenant = getTenantById(id);
        if (tenant != null) {
            mongoTemplate.remove(tenant);
            return tenant;
        }
        return null;
    }
}
