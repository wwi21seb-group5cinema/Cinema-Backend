package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String userName = "testUser", password = "testPassword", firstName = "testFirstName",
                lastName = "testSecondName", email = "testEmail", street = "testStreet",
                houseNumber = "testHouseNumber", cityName = "Wallstadt", plz = "68259";
        Role role = Role.USER;
        City city = new City(plz, cityName);
        User firstUser = new User(userName, password, role, firstName, lastName, email, city, street, houseNumber);
        User secondUser = new User(userName, password, role, firstName, lastName, email, city, street, houseNumber);

        assertAll(
                "Asserting equality...",
                () -> assertEquals(firstUser, secondUser, "Equals returns wrong result"),
                () -> assertEquals(firstUser.hashCode(), secondUser.hashCode(), "HashCode returns wrong result")
        );

    }

}
