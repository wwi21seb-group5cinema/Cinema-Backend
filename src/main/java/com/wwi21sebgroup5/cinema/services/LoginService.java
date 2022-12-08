package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.Role;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.exceptions.EmailAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.PasswordsNotMatchingException;
import com.wwi21sebgroup5.cinema.exceptions.UserAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.CityRepository;
import com.wwi21sebgroup5.cinema.repositories.UserRepository;
import com.wwi21sebgroup5.cinema.requestObjects.LoginRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.RegistrationRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    public User register(RegistrationRequestObject registrationObject) throws PasswordsNotMatchingException,
            UserAlreadyExistsException, EmailAlreadyExistsException {
        if (!registrationObject.getPassword().equals(registrationObject.getConfirmPassword())) {
            throw new PasswordsNotMatchingException(registrationObject.getUserName());
        }

        Optional<User> foundUser = userRepository.findByUserName(registrationObject.getUserName());

        if (foundUser.isEmpty()) {
            foundUser = userRepository.findByEmail(registrationObject.getEmail());

            if (foundUser.isPresent()) {
                throw new EmailAlreadyExistsException(registrationObject.getEmail());
            }
        } else {
            throw new UserAlreadyExistsException(registrationObject.getUserName());
        }

        Optional<City> foundCity = cityRepository.findByPlz(registrationObject.getPlz());

        if (foundCity.isEmpty()) {
            foundCity = cityRepository.findByName(registrationObject.getCityName());

            if (foundCity.isEmpty()) {
                cityRepository.save(new City(registrationObject.getPlz(), registrationObject.getCityName()));
                foundCity = cityRepository.findByPlz(registrationObject.getPlz());
            }
        }

        User newUser = new User(registrationObject.getUserName(),
                                registrationObject.getPassword(),
                                new Role("user", "User role"),
                                registrationObject.getFirstName(),
                                registrationObject.getLastName(),
                                registrationObject.getEmail(),
                                foundCity.get(),
                                registrationObject.getStreet(),
                                registrationObject.getHouseNumber());

        userRepository.save(newUser);
        return newUser;
    }

    public User login(LoginRequestObject loginObject) throws PasswordsNotMatchingException {
        Optional<User> foundUser = userRepository.findByUserName(loginObject.getUserName());

        if (foundUser.isEmpty()) {
            throw new UsernameNotFoundException(loginObject.getUserName());
        }

        if (!foundUser.get().getPassword().equals(loginObject.getPassword())) {
            throw new PasswordsNotMatchingException(loginObject.getUserName());
        }

        return foundUser.get();
    }
}
