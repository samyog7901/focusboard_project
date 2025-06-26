package org.focusboard.service;

import org.focusboard.dto.UserDTO;
import org.focusboard.model.UserModel;
import org.focusboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public void resetPassword(String email, String newPassword) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);  // This line persists the change
    }

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    // ✅ Register new user
    public UserModel registerUser(UserDTO dto) {


        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        if (!dto.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            throw new RuntimeException("Invalid email format");
        }
        if (dto.getPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }


        UserModel user = new UserModel();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Hash password
        user.setRole("USER");

        return userRepository.save(user);
    }

    // ✅ Find user by email
    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ✅ Validate password during login
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // ✅ Authenticate and return user if password matches
    public UserModel authenticateUser(String email, String rawPassword) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }
}