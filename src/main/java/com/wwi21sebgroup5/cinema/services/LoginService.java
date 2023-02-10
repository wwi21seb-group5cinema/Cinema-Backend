package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Token;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.enums.Role;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.requestObjects.ChangePasswordRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.LoginRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.RegistrationRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CityService cityService;

    @Autowired
    private EmailService emailService;

    private static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

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

        // Save user and create new token to confirm registration
        userService.save(newUser);
        String token = UUID.randomUUID().toString();
        Token registrationToken = tokenService.save(new Token(token, newUser));
        //emailService.sendRegistrationConfirmation(newUser, token);

        return newUser;
    }

    /**
     * @param loginObject DTO which holds username and password of the login form
     * @throws PasswordsNotMatchingException Thrown when password doesn't match the found users password
     * @throws UsernameNotFoundException     Thrown when the username wasn't found
     */
    public User login(LoginRequestObject loginObject) throws PasswordsNotMatchingException, EmailNotFoundException, UserNotEnabledException {
        Optional<User> foundUser = userService.getUserByEmail(loginObject.getEmail());

        if (foundUser.isEmpty()) {
            throw new EmailNotFoundException(loginObject.getEmail());
        }

        if (!foundUser.get().getEnabled()) {
            throw new UserNotEnabledException(foundUser.get().getUserName());
        }

        if (!passwordEncoder.matches(loginObject.getPassword(), foundUser.get().getPassword())) {
            throw new PasswordsNotMatchingException(loginObject.getEmail());
        }

        return foundUser.get();
    }

    public void confirmToken(String token) throws TokenNotFoundException, TokenExpiredException,
            TokenAlreadyConfirmedException {
        Optional<Token> foundToken = tokenService.findByTokenValue(token);
        Token tokenToConfirm;

        if (foundToken.isEmpty()) {
            throw new TokenNotFoundException(token);
        }

        LocalDateTime now = LocalDateTime.now();
        tokenToConfirm = foundToken.get();

        // First check if the token was already confirmed
        if (tokenToConfirm.getConfirmationDate() != null) {
            throw new TokenAlreadyConfirmedException();
        }

        // If the token expired, we simply send a new token to the email and set the expiration date to one day from now
        if (now.isAfter(tokenToConfirm.getExpirationDate())) {
            tokenToConfirm.setToken(UUID.randomUUID().toString());
            tokenToConfirm.setExpirationDate(now.plusDays(1));
            //emailService.sendRegistrationConfirmation(tokenToConfirm.getUser(), token);
            throw new TokenExpiredException();
        }

        // Set confirmation date and set enabled to true
        tokenToConfirm.setConfirmationDate(now);
        tokenService.save(tokenToConfirm);

        User userToUpdate = tokenToConfirm.getUser();
        userToUpdate.setEnabled(true);
        userService.save(userToUpdate);

        //emailService.sendTokenConfirmation(tokenToConfirm.getUser());
    }

    public void forgotPassword(String email) throws EmailNotFoundException {
        Optional<User> foundUser = userService.getUserByEmail(email);

        if (foundUser.isEmpty()) {
            throw new EmailNotFoundException(email);
        }

        User user = foundUser.get();
        String randomPassword = alphaNumericString(10);
        user.setPassword(passwordEncoder.encode(randomPassword));
        emailService.sendPasswordReset(user, randomPassword);
    }

    public void updateData(RegistrationRequestObject requestObject, UUID id) throws UserDoesNotExistException {
        Optional<User> foundUser = userService.getUserById(id);

        if (foundUser.isEmpty()) {
            throw new UserDoesNotExistException(id);
        }

        User user = foundUser.get();
        user.setCity(cityService.findByPlzAndName(requestObject.getPlz(), requestObject.getCityName()));
        user.setStreet(requestObject.getStreet());
        user.setHouseNumber(requestObject.getHouseNumber());
        user.setFirstName(requestObject.getFirstName());
        user.setLastName(requestObject.getLastName());
        user.setUserName(requestObject.getUserName());
        user.setEmail(requestObject.getEmail());
        userService.save(user);
    }

    public void changePassword(ChangePasswordRequestObject requestObject) throws UserDoesNotExistException {
        Optional<User> foundUser = userService.getUserById(requestObject.getId());

        if (foundUser.isEmpty()) {
            throw new UserDoesNotExistException(requestObject.getId());
        }

        User user = foundUser.get();
        user.setPassword(passwordEncoder.encode(requestObject.getNewPassword()));
        userService.save(user);
        emailService.sendPasswordChangeConfirmation(user);
    }
}
