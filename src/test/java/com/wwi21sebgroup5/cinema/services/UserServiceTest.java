package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.Role;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User getUser() {
        City city = new City("71672", "Marbach am Neckar");
        User user = new User("testUserName", "testPassword", Role.USER, "testFirstName",
                "testLastName", "testEmail", city, "testStreet", "testHouseNumber");
        user.setId(new UUID(1L, 1L));
        return user;
    }

    @Test
    @DisplayName("Test getting user by id")
    public void testGettingUserById() {
        User expectedUser = getUser();
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.getUserById(expectedUser.getId());
        assertEquals(actualUser.get(), expectedUser, "Returned wrong user");
    }

    @Test
    @DisplayName("Test getting user by username")
    public void testGettingUserByUserName() {
        User expectedUser = getUser();
        when(userRepository.findByUserName(expectedUser.getUserName())).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.getUserByUserName(expectedUser.getUserName());
        assertEquals(actualUser.get(), expectedUser, "Returned wrong user");
    }

    @Test
    @DisplayName("Test getting user by email")
    public void testGettingUserByEmail() {
        User expectedUser = getUser();
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.getUserByEmail(expectedUser.getEmail());
        assertEquals(actualUser.get(), expectedUser, "Returned wrong user");
    }

    @Test
    @DisplayName("Test getting all users")
    public void testGettingAllUsers() {
        User firstUser = getUser();
        User secondUser = new User("testU2", "testP2", Role.ADMIN, "testF2",
                "testL2", "testE2", firstUser.getCity(), "testS2", "testH2");
        User thirdUser = new User("testU3", "testP3", Role.ADMIN, "testF3",
                "testL3", "testE3", firstUser.getCity(), "testS3", "testH3");
        List<User> expectedUsers = List.of(firstUser, secondUser, thirdUser);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUsers();
        assertEquals(expectedUsers, actualUsers, "Returned wrong list of users");
    }

    @Test
    @DisplayName("Test saving")
    public void testSave() {
        User userToSave = getUser();

        when(userRepository.save(userToSave)).thenReturn(userToSave);

        userService.save(userToSave);
        verify(userRepository, times(1)).save(userToSave);
    }

}
