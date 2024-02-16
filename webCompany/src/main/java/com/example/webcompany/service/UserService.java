package com.example.webcompany.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.webcompany.entites.User;
import com.example.webcompany.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void creer(User user) throws Exception {
        User userFound = userRepository.findByEmail(user.getEmail());
        if (userFound == null) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            this.userRepository.save(user);
        } else {
            throw new Exception("User " + user.getEmail() + " is already in the database");
        }

    }

    public List<User> rechercher() {
        return this.userRepository.findAll();
    }

    public User lire(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public boolean authentification(String email, String password, boolean isFirstTime) {
        User user = userRepository.findByEmail(email);
        if (user == null || !passwordMatches(password, user.getPassword())) {
            throw new EntityNotFoundException("Authentication failed");
        }
        userRepository.updateIsFirstTime(isFirstTime, email);
        return true;
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        return true;
    }

    @Transactional
    public boolean updatePassword(String email, String password) {
        int updatedCount = userRepository.updatePassword(email, password);
        if (updatedCount > 0) {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new EntityNotFoundException("Authentication failed");
            } else {
                return true;
            }
        } else {
            throw new EntityNotFoundException("Password change failed!");
        }
    }
}
