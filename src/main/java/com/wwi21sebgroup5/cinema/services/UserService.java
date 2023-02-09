package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * @param id Id which shall be searched for in the database
     * @return Returns user in the form of an optional
     */
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    /**
     * @param userName UserName which shall be searched for in the database
     * @return Returns user in the form of an optional
     */
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     * @param email Email which shall be searched for in the database
     * @return Returns user in the form of an optional
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * @return Returns all user in the database in the form of a list
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Persists given user in the databse
     *
     * @param newUser User to be persisted
     */
    public void save(User newUser) {
        userRepository.save(newUser);
    }

}
