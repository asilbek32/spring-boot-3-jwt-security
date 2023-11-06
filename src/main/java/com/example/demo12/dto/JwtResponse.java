package com.example.demo12.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String refreshToken;
}
