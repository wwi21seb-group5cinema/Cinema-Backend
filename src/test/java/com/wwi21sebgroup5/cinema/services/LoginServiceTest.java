package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.Token;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.enums.Role;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.requestObjects.LoginRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.RegistrationRequestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private CityService cityService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private LoginService loginService;

    @Test
    @DisplayName("Test successful registration for normal user")
    public void testSuccessfulRegistrationNormaluser() {
        String userName = "TestUserName", password = "TestPassword", firstName = "TestFirstName",
                lastName = "TestLastName", email = "TestEmail", plz = "71672", cityName = "Marbach am Neckar",
                street = "TestStreet", houseNumber = "TestHouseNumber";
        boolean isAdmin = false;

        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                userName, password, firstName, lastName, email, plz, cityName, street, houseNumber, isAdmin
        );

        City city = new City(plz, cityName);

        User expectedUser = new User(
                userName, password, Role.USER, firstName, lastName, email, city, street, houseNumber
        );

        when(userService.getUserByUserName(userName)).thenReturn(Optional.empty());
        when(userService.getUserByEmail(email)).thenReturn(Optional.empty());
        when(cityService.findByPlzAndName(plz, cityName)).thenReturn(city);
        when(tokenService.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        doNothing().when(emailService).sendRegistrationConfirmation(any(), anyString());
        when(passwordEncoder.encode(password)).thenReturn(password);

        User actualUser = null;

        try {
            actualUser = loginService.register(registrationRequestObject);
        } catch (UserAlreadyExistsException | EmailAlreadyExistsException ex) {
            fail("Test failed during registering a user");
        }

        assertEquals(expectedUser, actualUser, "Registered user wrong!");
        verify(tokenService, times(1)).save(any());
        verify(emailService, times(1)).sendRegistrationConfirmation(any(), anyString());
    }

    @Test
    @DisplayName("Test successful registration for admin user")
    public void testSuccessfulRegistrationAdminUser() {
        String userName = "TestUserName", password = "TestPassword", firstName = "TestFirstName",
                lastName = "TestLastName", email = "TestEmail", plz = "71672", cityName = "Marbach am Neckar",
                street = "TestStreet", houseNumber = "TestHouseNumber";
        boolean isAdmin = true;

        RegistrationRequestObject registrationRequestObject = new RegistrationRequestObject(
                userName, password, firstName, lastName, email, plz, cityName, street, houseNumber, isAdmin
        );

        City city = new City(plz, cityName);

        User expectedUser = new User(
                userName, password, Role.ADMIN, firstName, lastName, email, city, street, houseNumber
        );

        when(userService.getUserByUserName(userName)).thenReturn(Optional.empty());
        when(userService.getUserByEmail(email)).thenReturn(Optional.empty());
        when(cityService.findByPlzAndName(plz, cityName)).thenReturn(city);
        when(tokenService.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        doNothing().when(emailService).sendRegistrationConfirmation(any(), anyString());
        when(passwordEncoder.encode(password)).thenReturn(password);

        User actualUser = null;

        try {
            actualUser = loginService.register(registrationRequestObject);
        } catch (UserAlreadyExistsException | EmailAlreadyExistsException ex) {
            fail("Test failed during registering a user");
        }

        assertEquals(expectedUser, actualUser, "Registered user wrong!");
        verify(tokenService, times(1)).save(any());
        verify(emailService, times(1)).sendRegistrationConfirmation(any(), anyString());
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
    @DisplayName("Test successful login")
    public void testSuccessfulLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject();
        String testEmail = "TestEmail";
        loginRequestObject.setEmail(testEmail);
        loginRequestObject.setPassword("TestPassword");
        User user = new User();
        user.setFirstName("TestFirstName");
        user.setPassword("TestPassword");
        user.setEnabled(true);
        when(userService.getUserByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestObject.getPassword(), user.getPassword())).thenReturn(true);

        try {
            loginService.login(loginRequestObject);
        } catch (PasswordsNotMatchingException | EmailNotFoundException | UserNotEnabledException e) {
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
    @DisplayName("Test passwords don't match during login")
    public void testPasswordsNotMatchingDuringLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject();
        String testEmail = "TestEmail";
        loginRequestObject.setEmail(testEmail);
        loginRequestObject.setPassword("TestPassword");
        User user = new User();
        user.setFirstName("TestFirstName");
        user.setPassword("TestPassword");
        user.setEnabled(true);
        when(userService.getUserByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestObject.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(PasswordsNotMatchingException.class, () -> loginService.login(loginRequestObject));
    }

    @Test
    @DisplayName("Test user not enabled during login")
    public void testUserNotEnabledDuringLogin() {
        LoginRequestObject loginRequestObject = new LoginRequestObject();
        String testEmail = "TestEmail";
        loginRequestObject.setEmail(testEmail);
        loginRequestObject.setPassword("TestPassword");
        User user = new User();
        user.setFirstName("TestFirstName");
        user.setPassword("TestPassword");
        user.setEnabled(false);
        when(userService.getUserByEmail(testEmail)).thenReturn(Optional.of(user));

        assertThrows(UserNotEnabledException.class, () -> loginService.login(loginRequestObject));
    }

    private Token generateToken() {
        Token token = new Token(UUID.randomUUID().toString(), new User());
        token.setExpirationDate(LocalDateTime.of(2023, 2, 12, 2, 30, 4));
        token.setId(UUID.randomUUID());
        return token;
    }

    @Test
    @DisplayName("Test token confirmation successful")
    public void testConfirmTokenSuccessful() {
        Token token = generateToken();
        Token expectedToken = generateToken();
        expectedToken.setId(token.getId());
        expectedToken.setToken(token.getToken());
        User expectedUser = expectedToken.getUser();
        expectedUser.setEnabled(true);

        LocalDateTime confirmationDateTime =
                LocalDateTime.of(2023, 2, 12, 2, 30, 3);
        expectedToken.setConfirmationDate(confirmationDateTime);

        when(tokenService.findByTokenValue(token.getToken())).thenReturn(Optional.of(token));
        when(tokenService.save(expectedToken)).thenReturn(expectedToken);
        doNothing().when(emailService).sendTokenConfirmation(expectedUser);
        try (MockedStatic<LocalDateTime> dateTimeMock = mockStatic(LocalDateTime.class)) {
            dateTimeMock.when(LocalDateTime::now).thenReturn(confirmationDateTime);

            // No further testing required if method doesn't throw any exception, since we confirm
            // correct calling of the methods called in it
            assertDoesNotThrow(() -> loginService.confirmToken(token.getToken()));
        }
    }

    @Test
    @DisplayName("Test token not found when confirming")
    public void testTokenNotFoundWhileConfirming() {
        String token = UUID.randomUUID().toString();

        when(tokenService.findByTokenValue(token)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> loginService.confirmToken(token));
    }

    @Test
    @DisplayName("Test token already expired when confirming")
    public void testTokenExpiredWhileConfirming() {
        Token token = generateToken();
        LocalDateTime dateTime =
                LocalDateTime.of(2023, 2, 12, 2, 30, 5);

        when(tokenService.findByTokenValue(token.getToken())).thenReturn(Optional.of(token));
        try (MockedStatic<LocalDateTime> dateTimeMock = mockStatic(LocalDateTime.class)) {
            dateTimeMock.when(LocalDateTime::now).thenReturn(dateTime);

            // No further testing required if method doesn't throw any exception, since we confirm
            // correct calling of the methods called in it
            assertThrows(TokenExpiredException.class, () -> loginService.confirmToken(token.getToken()));
        }
    }

    @Test
    @DisplayName("Test token already confirmed")
    public void testTokenAlreadyConfirmed() {
        Token token = generateToken();
        LocalDateTime dateTime =
                LocalDateTime.of(2023, 2, 12, 2, 30, 5);
        token.setConfirmationDate(token.getExpirationDate().minusHours(2));

        when(tokenService.findByTokenValue(token.getToken())).thenReturn(Optional.of(token));
        try (MockedStatic<LocalDateTime> dateTimeMock = mockStatic(LocalDateTime.class)) {
            dateTimeMock.when(LocalDateTime::now).thenReturn(dateTime);

            // No further testing required if method doesn't throw any exception, since we confirm
            // correct calling of the methods called in it
            assertThrows(TokenAlreadyConfirmedException.class, () -> loginService.confirmToken(token.getToken()));
        }
    }

}
