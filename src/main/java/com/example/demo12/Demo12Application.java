package com.example.demo12;

import com.example.demo12.entities.User;
import com.example.demo12.enums.Role;
import com.example.demo12.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Demo12Application implements CommandLineRunner {
	@Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Demo12Application.class, args);
	}

	public void run(String... args){
		User adminAccount = repository.findByRole(Role.ADMIN);
		if (null == adminAccount){
			User user = new User();
			user.setEmail("admin@gmai.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			repository.save(user);
		}
	}

}
