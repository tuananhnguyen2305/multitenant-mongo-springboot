package com.example.mongotest.filter;

import com.example.mongotest.configuration.MongoConnectionManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TenantFilter extends OncePerRequestFilter {
    private static final String TENANT_HEADER = "X-TenantId";
    @Value("${spring.data.mongo.uri}")
    private static String CONNECTION_STRING;
    private static final String TENANT_REPLACEMENT = "centraldb";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(TENANT_HEADER);
        if (header == null || header.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String dbConnectionString = CONNECTION_STRING.replace(TENANT_REPLACEMENT, header);
            MongoConnectionManager.setDatabase(dbConnectionString);

            filterChain.doFilter(request, response);
            MongoConnectionManager.clear();
        }
    }
}
