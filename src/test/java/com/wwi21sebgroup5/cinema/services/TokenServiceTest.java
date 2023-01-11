package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Token;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.repositories.TokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @Test
    @DisplayName("Test saving")
    public void testSaveToken() {
        Token token = new Token(UUID.randomUUID().toString(), new User());

        when(tokenRepository.save(token)).thenReturn(token);
        assertEquals(token, tokenService.save(token));
    }

    @Test
    @DisplayName("Test get by token value")
    public void testGetTokenByValue() {
        Token token = new Token(UUID.randomUUID().toString(), new User());

        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));
        assertEquals(token, tokenService.findByTokenValue(token.getToken()).get());
    }

}
