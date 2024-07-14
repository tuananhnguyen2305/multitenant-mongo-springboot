package com.example.mongotest.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    int status;
    String token;
    boolean isSuccess;
    Object data;
}
