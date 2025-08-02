package com.techHorizon.tech.service;

import com.techHorizon.tech.models.User;
import com.techHorizon.tech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password)); // Encripta la contraseña

        return userRepository.save(newUser);
    }

    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.filter(u -> passwordEncoder.matches(password, u.getPassword()));
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}

