package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.Role;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Test getting all users successfully")
    public void testGetAllUsersSuccessful() throws Exception {
        City city = new City("71672", "Marbach am Neckar");
        User firstUser = new User(
                "TestUserName1", "TestPassword1", Role.ADMIN, "TestFirstName1",
                "TestLastName1", "TestEmail1", city, "TestStreet1", "TestHouseNumber1"
        );
        User secondUser = new User(
                "TestUserName2", "TestPassword2", Role.USER, "TestFirstName2",
                "TestLastName2", "TestEmail2", city, "TestStreet2", "TestHouseNumber2"
        );
        List<User> expectedUsers = List.of(firstUser, secondUser);

        when(userService.getAllUsers()).thenReturn(expectedUsers);

        mockMvc.
                perform(get("/v1/user/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("TestUserName1"))
                .andExpect(jsonPath("$[1].userName").value("TestUserName2"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Test getting all users not successfully")
    public void testGetAllUsersNotSuccessful() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of());

        mockMvc.
                perform(get("/v1/user/getAll"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Test getting user by id successfully")
    public void testGetUserByIdSuccessful() throws Exception {
        City city = new City("71672", "Marbach am Neckar");
        User expectedUser = new User(
                "TestUserName1", "TestPassword1", Role.ADMIN, "TestFirstName1",
                "TestLastName1", "TestEmail1", city, "TestStreet1", "TestHouseNumber1"
        );
        UUID uuid = new UUID(2L, 2L);
        expectedUser.setId(uuid);

        when(userService.getUserById(uuid)).thenReturn(Optional.of(expectedUser));

        mockMvc.
                perform(get(String.format("/v1/user/%s", uuid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").value("TestUserName1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Test getting user by id not successfully")
    public void testGetUserByIdNotSuccessful() throws Exception {
        UUID uuid = new UUID(2L, 2L);
        when(userService.getUserById(uuid)).thenReturn(Optional.empty());

        mockMvc.
                perform(get(String.format("/v1/user/%s", uuid)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("userName").doesNotExist());
    }

}
