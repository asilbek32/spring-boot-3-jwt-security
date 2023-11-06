package com.example.demo12.controller;

import com.example.demo12.dto.JwtResponse;
import com.example.demo12.dto.LoginDTO;
import com.example.demo12.dto.RefreshTokenRequest;
import com.example.demo12.dto.RegisterDTO;
import com.example.demo12.entities.User;
import com.example.demo12.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDTO dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest dto){
        return ResponseEntity.ok(authService.refreshToken(dto));
    }
}
