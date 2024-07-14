package com.example.mongotest.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthenticationResponse {
    String token;
    boolean authenticated;
    Object data;
}