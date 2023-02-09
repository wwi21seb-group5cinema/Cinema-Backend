package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Token;
import com.wwi21sebgroup5.cinema.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * @param newToken Token to be saved
     * @return Returns token with the auto-generated id
     */
    public Token save(Token newToken) {
        return tokenRepository.save(newToken);
    }

    public List<Token> getAll() {
        return tokenRepository.findAll();
    }

    public Optional<Token> findByTokenValue(String token) {
        return tokenRepository.findByToken(token);
    }

}
