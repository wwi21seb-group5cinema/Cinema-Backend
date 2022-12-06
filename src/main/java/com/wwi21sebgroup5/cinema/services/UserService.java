package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public User register(String username) {
        return userRepository.findByUserName(username);
    }

}
