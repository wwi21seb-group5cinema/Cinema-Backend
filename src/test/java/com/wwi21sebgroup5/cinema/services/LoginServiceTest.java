package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.Role;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.requestObjects.LoginRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.RegistrationRequestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @Mock
    private CityService cityService;

    @InjectMocks
    private LoginService loginService;

    @Test
    @DisplayName("Test successful registration")
    public void testSuccessfulRegistration() {
        String userName = "TestUserName", password = "TestPassword", firstName = "TestFirstName",
                lastName = "TestLastName", email = "TestEmail", plz = "71672", cityName = "Marbach am Neckar",
                street = "TestStreet", houseNumber = "TestHouseNumber";
        boolean isAdmin = false;

        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                userName, password, firstName, lastName, email, plz, cityName, street, houseNumber, isAdmin
        );

        City city = new City();
        city.setName(cityName);
        city.setPlz(plz);

        User expectedUser = new User(
                userName, password, Role.USER, firstName, lastName, email, city, street, houseNumber
        );

        when(userService.getUserByUserName(userName)).thenReturn(Optional.empty());
        when(userService.getUserByEmail(email)).thenReturn(Optional.empty());
        when(cityService.findByPlzAndName(plz, cityName)).thenReturn(Optional.of(city));
        when(passwordEncoder.encode(password)).thenReturn(password);

        User actualUser = null;

        try {
            actualUser = loginService.register(registrationRequestObject);
        } catch (UserAlreadyExistsException | EmailAlreadyExistsException |
                 CityNotFoundException e) {
            fail("Test failed during registering a user");
        }

        assertEquals(expectedUser, actualUser, "Registered user wrong!");

    }

    @Test
    @DisplayName("Test registration when username already exists")
    public void testUsernameAlreadyExistsDuringRegistration() {
        String userName = "TestUserName", password = "TestPassword", firstName = "TestFirstName",
                lastName = "TestLastName", email = "TestEmail", plz = "71672", cityName = "Marbach am Neckar",
                street = "TestStreet", houseNumber = "TestHouseNumber";
        boolean isAdmin = false;

        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                userName, password, firstName, lastName, email, plz, cityName, street, houseNumber, isAdmin
        );

        when(userService.getUserByUserName(userName)).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> loginService.register(registrationRequestObject));
    }

    @Test
    @DisplayName("Test registration when email already exists")
    public void testEmailAlreadyExistsDuringRegistration() {
        String userName = "TestUserName", password = "TestPassword", firstName = "TestFirstName",
                lastName = "TestLastName", email = "TestEmail", plz = "71672", cityName = "Marbach am Neckar",
                street = "TestStreet", houseNumber = "TestHouseNumber";
        boolean isAdmin = false;

        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                userName, password, firstName, lastName, email, plz, cityName, street, houseNumber, isAdmin
        );

        when(userService.getUserByEmail(email)).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> loginService.register(registrationRequestObject));
    }

    @Test
    @DisplayName("Test city not found during registration")
    public void testCityNotFoundDuringRegistration() {
        String userName = "TestUserName", password = "TestPassword", firstName = "TestFirstName",
                lastName = "TestLastName", email = "TestEmail", plz = "71672", cityName = "Marbach am Neckar",
                street = "TestStreet", houseNumber = "TestHouseNumber";
        boolean isAdmin = false;

        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                userName, password, firstName, lastName, email, plz, cityName, street, houseNumber, isAdmin
        );

        when(cityService.findByPlzAndName(plz, cityName)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> loginService.register(registrationRequestObject));
    }

    @Test
    @DisplayName("Test successful login")
    public void testSuccessfulLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject();
        String testEmail = "TestEmail";
        loginRequestObject.setEmail(testEmail);
        loginRequestObject.setPassword("TestPassword");
        User user = new User();
        user.setFirstName("TestFirstName");
        user.setPassword("TestPassword");
        when(userService.getUserByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestObject.getPassword(), user.getPassword())).thenReturn(true);

        try {
            loginService.login(loginRequestObject);
        } catch (PasswordsNotMatchingException | EmailNotFoundException e) {
            fail("Login not successful");
        }
    }

    @Test
    @DisplayName("Test username not found during login")
    public void testUserNotFoundDuringLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject();
        String testEmail = "TestEmail";
        loginRequestObject.setEmail(testEmail);
        loginRequestObject.setPassword("TestPassword");
        when(userService.getUserByEmail(testEmail)).thenReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class, () -> loginService.login(loginRequestObject));
    }

    @Test
    @DisplayName("Test passwords don't mtach during login")
    public void testPasswordsNotMatchingDuringLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject();
        String testEmail = "TestEmail";
        loginRequestObject.setEmail(testEmail);
        loginRequestObject.setPassword("TestPassword");
        User user = new User();
        user.setFirstName("TestFirstName");
        user.setPassword("TestPassword");
        when(userService.getUserByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestObject.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(PasswordsNotMatchingException.class, () -> loginService.login(loginRequestObject));
    }

}
