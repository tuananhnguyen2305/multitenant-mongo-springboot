package com.example.mongotest.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document
public class Tenant {
    @Id
    String id;
    String name;
    String databaseUri;
}
