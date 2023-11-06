package com.example.demo12.service;

import com.example.demo12.config.JwtService;
import com.example.demo12.dto.JwtResponse;
import com.example.demo12.dto.LoginDTO;
import com.example.demo12.dto.RefreshTokenRequest;
import com.example.demo12.dto.RegisterDTO;
import com.example.demo12.entities.User;
import com.example.demo12.enums.Role;
import com.example.demo12.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User register(RegisterDTO dto){
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

       return repository.save(user);

    }

    public JwtResponse login(LoginDTO dto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),dto.getPassword()));
        var user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);
        var refresh = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwt);
        jwtResponse.setRefreshToken(refresh);
        return jwtResponse;
    }

    public JwtResponse refreshToken(RefreshTokenRequest request){
        String userEmail = jwtService.extractUsername(request.getToken());
        User user = repository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(request.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(jwt);
            jwtResponse.setRefreshToken(request.getToken());
            return jwtResponse;
        }
        return null;
    }
}
