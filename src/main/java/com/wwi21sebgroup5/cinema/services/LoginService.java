package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.enums.Role;
import com.wwi21sebgroup5.cinema.exceptions.EmailAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.EmailNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.PasswordsNotMatchingException;
import com.wwi21sebgroup5.cinema.exceptions.UserAlreadyExistsException;
import com.wwi21sebgroup5.cinema.requestObjects.LoginRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.RegistrationRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private EmailService emailService;

    /**
     * @param registrationObject DTO which holds all necessary attributes for a new user
     * @return the registered User if registered successfully
     * @throws UserAlreadyExistsException  Thrown if the username already exists
     * @throws EmailAlreadyExistsException Thrown if the email already exists
     */
    public User register(RegistrationRequestObject registrationObject) throws UserAlreadyExistsException,
            EmailAlreadyExistsException {
        Optional<User> foundUser = userService.getUserByUserName(registrationObject.getUserName());
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException(registrationObject.getUserName());
        }

        foundUser = userService.getUserByEmail(registrationObject.getEmail());
        if (foundUser.isPresent()) {
            throw new EmailAlreadyExistsException(registrationObject.getEmail());
        }

        User newUser = new User(registrationObject.getUserName(),
                passwordEncoder.encode(registrationObject.getPassword()),
                registrationObject.isAdmin() ? Role.ADMIN : Role.USER,
                registrationObject.getFirstName(),
                registrationObject.getLastName(),
                registrationObject.getEmail(),
                cityService.findByPlzAndName(registrationObject.getPlz(), registrationObject.getCityName()),
                registrationObject.getStreet(),
                registrationObject.getHouseNumber());

        userService.save(newUser);
        emailService.sendRegistrationConfirmation(newUser, "jaja kommt noch");
        return newUser;
    }

    /**
     * @param loginObject DTO which holds username and password of the login form
     * @throws PasswordsNotMatchingException Thrown when password doesn't match the found users password
     * @throws UsernameNotFoundException     Thrown when the username wasn't found
     */
    public User login(LoginRequestObject loginObject) throws PasswordsNotMatchingException, EmailNotFoundException {
        Optional<User> foundUser = userService.getUserByEmail(loginObject.getEmail());

        if (foundUser.isEmpty()) {
            throw new EmailNotFoundException(loginObject.getEmail());
        }

        if (!passwordEncoder.matches(loginObject.getPassword(), foundUser.get().getPassword())) {
            throw new PasswordsNotMatchingException(loginObject.getEmail());
        }

        return foundUser.get();
    }
}
