package com.example.mongotest.controller;

import com.example.mongotest.configuration.MongoConnectionManager;
import com.example.mongotest.entity.Tenant;
import com.example.mongotest.payload.ResponseData;
import com.example.mongotest.service.DatabaseService;
import com.example.mongotest.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    TenantService tenantService;

    @Autowired
    DatabaseService databaseService;

    @GetMapping
    public ResponseEntity<?> getAllTenants() {
        ResponseData responseData = new ResponseData();
        try {
            responseData.setData(tenantService.getAll());
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseData.setStatus(400);
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTenantById(@PathVariable String id) {
        ResponseData responseData = new ResponseData();

        Tenant tenant = tenantService.getTenantById(id);
        if (tenant != null) {
            responseData.setData(tenant);
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setData("Get tenant fail");
            responseData.setStatus(400);
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }

    @PostMapping
    public ResponseEntity<?> createTenant(@RequestBody Tenant tenant) {
        MongoConnectionManager.setDatabase("centraldb");
        ResponseData responseData = new ResponseData();
        try {
            databaseService.createDb(tenant.getName(), tenant.getDatabaseUri());
            responseData.setData(tenantService.save(tenant));
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } catch (Exception e) {
            responseData.setData("Create tenant fail");
            responseData.setStatus(400);
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTenantById(@PathVariable String id) {
        ResponseData responseData = new ResponseData();

        Tenant tenant = tenantService.deleteTenantById(id);
        if (tenant != null) {
            responseData.setData(tenant);
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setData("Delete tenant fail");
            responseData.setStatus(400);
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatusCode.valueOf(responseData.getStatus()));
    }


}
