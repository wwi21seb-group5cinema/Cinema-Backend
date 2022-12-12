package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.exceptions.EmailNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.PasswordsNotMatchingException;
import com.wwi21sebgroup5.cinema.requestObjects.LoginRequestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
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
