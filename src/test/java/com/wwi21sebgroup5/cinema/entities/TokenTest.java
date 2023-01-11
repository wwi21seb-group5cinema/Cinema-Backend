package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TokenTest {

    private Token generateToken(UUID id, String token) {
        Token newToken = new Token(token, new User());
        newToken.setExpirationDate(LocalDateTime.of(2023, 2, 12, 2, 30));
        newToken.setConfirmationDate(newToken.getExpirationDate().plusHours(2));
        newToken.setId(id);
        return newToken;
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        Token firstToken = generateToken(UUID.randomUUID(), UUID.randomUUID().toString());
        Token secondToken = generateToken(firstToken.getId(), firstToken.getToken());

        // validate equality
        assertEquals(firstToken, secondToken);
        assertEquals(firstToken.hashCode(), secondToken.hashCode());

        // validate this == that
        secondToken = firstToken;
        assertEquals(firstToken, secondToken);

        // different class and null
        assertNotEquals(firstToken, "");
        assertNotEquals(firstToken, null);

        // different id
        secondToken = generateToken(UUID.randomUUID(), firstToken.getToken());
        assertNotEquals(firstToken, secondToken);

        // different token
        secondToken = generateToken(firstToken.getId(), firstToken.getToken());
        secondToken.setToken(UUID.randomUUID().toString());
        assertNotEquals(firstToken, secondToken);

        // different user
        secondToken = generateToken(firstToken.getId(), firstToken.getToken());
        secondToken.setUser(new User("a", "b", Role.USER, "c", "d", "e",
                new City("f", "g"), "h", "i"));
        assertNotEquals(firstToken, secondToken);
        assertNotEquals(firstToken.hashCode(), secondToken.hashCode());

        // different confirmation date
        secondToken = generateToken(firstToken.getId(), firstToken.getToken());
        secondToken.setConfirmationDate(firstToken.getConfirmationDate().plusMinutes(2));
        assertNotEquals(firstToken, secondToken);

        // different expiration date
        secondToken = generateToken(firstToken.getId(), firstToken.getToken());
        secondToken.setExpirationDate(firstToken.getExpirationDate().plusMinutes(2));
        assertNotEquals(firstToken, secondToken);

        firstToken = new Token();
        secondToken = new Token();

        assertEquals(firstToken, secondToken);
        assertEquals(firstToken.hashCode(), secondToken.hashCode());
    }

}
