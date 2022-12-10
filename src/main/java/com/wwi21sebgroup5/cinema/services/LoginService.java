package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.Role;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.exceptions.CityNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.EmailAlreadyExistsException;
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

    /**
     * @param registrationObject DTO which holds all neccessary attributes for a new user
     * @return the registered User if registered successfully
     * @throws PasswordsNotMatchingException Thrown if passwords don't match
     * @throws UserAlreadyExistsException    Thrown if the username already exists
     * @throws EmailAlreadyExistsException   Thrown if the email already exists
     */
    public User register(RegistrationRequestObject registrationObject) throws PasswordsNotMatchingException,
            UserAlreadyExistsException, EmailAlreadyExistsException, CityNotFoundException {
        if (!registrationObject.getPassword().equals(registrationObject.getConfirmPassword())) {
            throw new PasswordsNotMatchingException(registrationObject.getUserName());
        }

        Optional<User> foundUser = userService.getUserByUserName(registrationObject.getUserName());

        if (foundUser.isEmpty()) {
            foundUser = userService.getUserByEmail(registrationObject.getEmail());

            if (foundUser.isPresent()) {
                throw new EmailAlreadyExistsException(registrationObject.getEmail());
            }
        } else {
            throw new UserAlreadyExistsException(registrationObject.getUserName());
        }

        Optional<City> foundCity = cityService.
                findByPlzAndName(registrationObject.getPlz(), registrationObject.getCityName());

        if (foundCity.isEmpty()) {
            throw new CityNotFoundException(registrationObject.getPlz(), registrationObject.getCityName());
        }

        User newUser = new User(registrationObject.getUserName(),
                passwordEncoder.encode(registrationObject.getPassword()),
                registrationObject.isAdmin() ? Role.ADMIN : Role.USER,
                registrationObject.getFirstName(),
                registrationObject.getLastName(),
                registrationObject.getEmail(),
                foundCity.get(),
                registrationObject.getStreet(),
                registrationObject.getHouseNumber());

        userService.save(newUser);
        return newUser;
    }

    /**
     * @param loginObject DTO which holds username and password of the login form
     * @throws PasswordsNotMatchingException Thrown when password doesn't match the found users password
     * @throws UsernameNotFoundException     Thrown when the username wasn't found
     */
    public void login(LoginRequestObject loginObject) throws UsernameNotFoundException, PasswordsNotMatchingException {
        Optional<User> foundUser = userService.getUserByUserName(loginObject.getUserName());

        if (foundUser.isEmpty()) {
            throw new UsernameNotFoundException(loginObject.getUserName());
        }

        if (!passwordEncoder.matches(loginObject.getPassword(), foundUser.get().getPassword())) {
            throw new PasswordsNotMatchingException(loginObject.getUserName());
        }
    }
}
