package com.example.demo12.repository;

import com.example.demo12.entities.User;
import com.example.demo12.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);
   User findByRole(Role role);
}
