package com.example.mongotest.repository;

import com.example.mongotest.entity.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITenantRepo extends MongoRepository<Tenant, String> {
    Tenant findByName(String name);
}
