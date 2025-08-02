package com.techHorizon.tech.service;

import com.techHorizon.tech.models.User;
import com.techHorizon.tech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
                        user.setUsername(updatedUser.getUsername());
                    }
                    if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
                        user.setEmail(updatedUser.getEmail());
                    }
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        user.setPassword(updatedUser.getPassword());
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
