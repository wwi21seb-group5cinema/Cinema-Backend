package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    @DisplayName("Testing the constructor")
    public void constructorTest() {
        String userName = "testUser", password = "testPassword", firstName = "testFirstName",
                lastName = "testSecondName", email = "testEmail", street = "testStreet",
                houseNumber = "testHouseNumber", cityName = "Wallstadt", plz = "68259";
        Role role = Role.USER;
        City city = new City(plz, cityName);
        User user = new User(userName, password, role, firstName, lastName, email, city, street, houseNumber);

        assertAll(
                "Testing all parameters",
                () -> assertEquals(userName, user.getUserName(), "Username returned wrong"),
                () -> assertEquals(password, user.getPassword(), "Password returned wrong"),
                () -> assertEquals(role, user.getRole(), "Role returned wrong"),
                () -> assertEquals(firstName, user.getFirstName(), "FirstName returned wrong"),
                () -> assertEquals(lastName, user.getLastName(), "LastName returned wrong"),
                () -> assertEquals(email, user.getEmail(), "Email returned wrong"),
                () -> assertEquals(city, user.getCity(), "City returned wrong"),
                () -> assertEquals(street, user.getStreet(), "Street returned wrong"),
                () -> assertEquals(houseNumber, user.getHouseNumber(), "HouseNumber returned wrong")
        );
    }

    @Test
    @DisplayName("Testing equality")
    public void equalityTest() {
        UUID id = UUID.randomUUID();
        User first = getUser(id);
        User second = getUser(id);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, first);

        assertNotEquals(first, "String");
        assertNotEquals(first, null);

        second.setFirstName(null);
        assertNotEquals(first, second);

        second = getUser(id);
        second.setPassword(null);
        assertNotEquals(first, second);

        second = getUser(id);
        second.setId(null);
        assertNotEquals(first, second);

        second = getUser(id);
        second.setEmail(null);
        assertNotEquals(first, second);

        second = getUser(id);
        second.setCity(null);
        assertNotEquals(first, second);

        second = getUser(id);
        second.setRole(null);
        assertNotEquals(first, second);

        second = getUser(id);
        second.setStreet(null);
        assertNotEquals(first, second);

        second = getUser(id);
        second.setHouseNumber(null);
        assertNotEquals(first, second);

        second = getUser(id);
        second.setUserName(null);
        assertNotEquals(first, second);

        second = getUser(id);
        second.setLastName(null);
        assertNotEquals(first, second);

        second = getUser(id);
        assertNotEquals(first.hashCode(), second.hashCode());

    }

    private User getUser(UUID id) {
        String userName = "testUser", password = "testPassword", firstName = "testFirstName",
                lastName = "testSecondName", email = "testEmail", street = "testStreet",
                houseNumber = "testHouseNumber", cityName = "Wallstadt", plz = "68259";
        Role role = Role.USER;
        City city = new City(plz, cityName);
        User user = new User(userName, password, role, firstName, lastName, email, city, street, houseNumber);

        user.setId(id);
        return user;
    }

    private User getUserNull() {
        return new User(null, null, null, null, null, null, null, null, null);
    }

}
