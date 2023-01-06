package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrentUserDetailsServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    CurrentUserDetailsService userDetailsService;

    private User getUser(boolean isAdmin) {
        City city = new City("71672", "Marbach am Neckar");
        Role role = isAdmin ? Role.ADMIN : Role.USER;
        return new User("testUserName", "testPassword", role, "testFirstName",
                "testLastName", "testEmail", city, "testStreet", "testHouseNumber");
    }

    @Test
    @DisplayName("Test getting user authority")
    public void testUserWithUserRole() {
        User user = getUser(false);
        when(userService.getUserByUserName(user.getUserName())).thenReturn(Optional.of(user));

        UserDetails actualUserDetails = userDetailsService.loadUserByUsername(user.getUserName());
        assertAll(
                "Validating properties of UserDetails..",
                () -> assertEquals(actualUserDetails.getUsername(), user.getUserName()),
                () -> assertEquals(actualUserDetails.getPassword(), user.getPassword()),
                () -> assertEquals(actualUserDetails.getAuthorities().size(), 1),
                () -> assertTrue(actualUserDetails.getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_USER")))
        );
    }

    @Test
    @DisplayName("Test getting admin authority")
    public void testUserWithAdminRole() {
        User user = getUser(true);
        when(userService.getUserByUserName(user.getUserName())).thenReturn(Optional.of(user));

        UserDetails actualUserDetails = userDetailsService.loadUserByUsername(user.getUserName());
        assertAll(
                "Validating properties of UserDetails..",
                () -> assertEquals(actualUserDetails.getUsername(), user.getUserName()),
                () -> assertEquals(actualUserDetails.getPassword(), user.getPassword()),
                () -> assertEquals(actualUserDetails.getAuthorities().size(), 1),
                () -> assertTrue(actualUserDetails.getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
        );

    }

    @Test
    @DisplayName("Test finding no user")
    public void testNoUserFound() {
        when(userService.getUserByUserName(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("LarryIsAKek"));
    }
}
