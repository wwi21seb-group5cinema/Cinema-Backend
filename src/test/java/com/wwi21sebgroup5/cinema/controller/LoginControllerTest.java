package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.enums.Role;
import com.wwi21sebgroup5.cinema.exceptions.EmailAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.EmailNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.PasswordsNotMatchingException;
import com.wwi21sebgroup5.cinema.exceptions.UserAlreadyExistsException;
import com.wwi21sebgroup5.cinema.requestObjects.LoginRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.RegistrationRequestObject;
import com.wwi21sebgroup5.cinema.services.LoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    LoginService loginService;

    @InjectMocks
    LoginController loginController;

    @Test
    @DisplayName("Test registering successfully")
    public void testSuccessfulRegistration() {
        City city = new City("67065", "Maudach");
        User expectedUser = new User(
                "TestUserName", "TestPassword", Role.USER, "TestFirstName",
                "TestLastName", "TestEmail", city, "TestStreet", "TestHouseNumber"
        );
        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                "TestUserName", "TestLastName", "TestFirstName", "TestLastName",
                "TestEmail", "67065", "Maudach", "TestStreet", "TestHouseNumber",
                false
        );

        try {
            when(loginService.register(registrationRequestObject)).thenReturn(expectedUser);
        } catch (UserAlreadyExistsException | EmailAlreadyExistsException e) {
            fail("Registration failed");
        }

        ResponseEntity<Object> response = loginController.register(registrationRequestObject);

        assertAll(
                "Validating response ...",
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals(expectedUser, response.getBody())
        );
    }

    @Test
    @DisplayName("Test user already exists during registration")
    public void testUserAlreadyExistsDuringRegistration() {
        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                "TestUserName", "TestLastName", "TestFirstName", "TestLastName",
                "TestEmail", "67065", "Maudach", "TestStreet", "TestHouseNumber",
                false
        );

        try {
            when(loginService.register(registrationRequestObject)).thenThrow(new UserAlreadyExistsException(
                    "TestUserName"));
        } catch (UserAlreadyExistsException | EmailAlreadyExistsException e) {
            fail("Registration failed");
        }

        ResponseEntity<Object> response = loginController.register(registrationRequestObject);

        assertAll(
                "Validating response ...",
                () -> assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode()),
                () -> assertEquals("User with the name TestUserName already exists", response.getBody())
        );
    }

    @Test
    @DisplayName("Test email already exists during registration")
    public void testEmailAlreadyExistsDuringRegistration() {
        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                "TestUserName", "TestLastName", "TestFirstName", "TestLastName",
                "TestEmail", "67065", "Maudach", "TestStreet", "TestHouseNumber",
                false
        );

        try {
            when(loginService.register(registrationRequestObject)).thenThrow(new EmailAlreadyExistsException(
                    "TestEmail"));
        } catch (UserAlreadyExistsException | EmailAlreadyExistsException e) {
            fail("Registration failed");
        }

        ResponseEntity<Object> response = loginController.register(registrationRequestObject);

        assertAll(
                "Validating response ...",
                () -> assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode()),
                () -> assertEquals("The email TestEmail is already in use!", response.getBody())
        );
    }

    @Test
    @DisplayName("Test internal server error during registration")
    public void testInternalServerErrorDuringRegistration() {
        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                "TestUserName", "TestLastName", "TestFirstName", "TestLastName",
                "TestEmail", "67065", "Maudach", "TestStreet", "TestHouseNumber",
                false
        );

        try {
            when(loginService.register(registrationRequestObject)).thenThrow(new RuntimeException("Error!"));
        } catch (UserAlreadyExistsException | EmailAlreadyExistsException e) {
            fail("Registration failed");
        }

        ResponseEntity<Object> response = loginController.register(registrationRequestObject);

        assertAll(
                "Validating response ...",
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
                () -> assertEquals("Error!", response.getBody())
        );
    }

    @Test
    @DisplayName("Test successful login")
    public void testSuccessfulLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject("TestEmail", "TestPassword");

        try {
            when(loginService.login(loginRequestObject)).thenReturn(new User());
        } catch (PasswordsNotMatchingException | EmailNotFoundException e) {
            fail("Login failed");
        }

        ResponseEntity<Object> response = loginController.login(loginRequestObject);

        assertAll(
                "Validating respones ...",
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }

    @Test
    @DisplayName("Test email not found during login")
    public void testEmailNotFoundDuringLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject("TestEmail", "TestPassword");

        try {
            doThrow(new EmailNotFoundException("TestEmail"))
                    .when(loginService)
                    .login(loginRequestObject);
        } catch (PasswordsNotMatchingException | EmailNotFoundException e) {
            fail("Login failed");
        }

        ResponseEntity<Object> response = loginController.login(loginRequestObject);

        assertAll(
                "Validating respones ...",
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                () -> assertEquals("No user found with the email TestEmail!", response.getBody())
        );
    }

    @Test
    @DisplayName("Test passwords not matching during login")
    public void testPasswordsNotMatchingDuringLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject("TestEmail", "TestPassword");

        try {
            doThrow(new PasswordsNotMatchingException("TestEmail"))
                    .when(loginService)
                    .login(loginRequestObject);
        } catch (PasswordsNotMatchingException | EmailNotFoundException e) {
            fail("Login failed");
        }

        ResponseEntity<Object> response = loginController.login(loginRequestObject);

        assertAll(
                "Validating respones ...",
                () -> assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode()),
                () -> assertEquals("Passwords for user with the email TestEmail don't match!",
                        response.getBody())
        );
    }

    @Test
    @DisplayName("Test internal server error during login")
    public void testInternalServerErrorDuringLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject("TestEmail", "TestPassword");

        try {
            doThrow(new RuntimeException("Error!"))
                    .when(loginService)
                    .login(loginRequestObject);
        } catch (PasswordsNotMatchingException | EmailNotFoundException e) {
            fail("Login failed");
        }

        ResponseEntity<Object> response = loginController.login(loginRequestObject);

        assertAll(
                "Validating respones ...",
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
                () -> assertEquals("Error!", response.getBody())
        );
    }

}
