package com.example.mongotest.entity;

import com.example.mongotest.entity.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    String id;
    String firstName;
    String lastName;
    String email;
    String password;
    String tenantId;
    Role role;

    public org.bson.Document toDocument() {
        org.bson.Document document = new org.bson.Document();
        document.append("id", id);
        document.append("firstName", firstName);
        document.append("lastName", lastName);
        document.append("email", email);
        document.append("password", password);
        document.append("tenantId", tenantId);
        document.append("role", role.name());

        return document;
    }
}
