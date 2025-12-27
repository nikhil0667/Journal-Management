package com.example.Journal.service;

import com.example.Journal.entity.User;
import com.example.Journal.repository.UserRepository;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public User saveUser(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            throw new RuntimeException("User Already Exists");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        existingUser.setUsername(user.getUsername());
        return userRepository.save(existingUser);
    }

    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User Not Found");
        }

        userRepository.deleteById(id);
        return "User Deleted Successfully";
    }


    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}
