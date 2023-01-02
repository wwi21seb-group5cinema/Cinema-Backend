package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Genre;
import com.wwi21sebgroup5.cinema.repositories.GenreRepository;
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

public class GenreServiceTest {

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    GenreService genreService;

    @Test
    @DisplayName("Test getting genre by Name")
    public void testGetGenreByName() {
        Genre expectedGenre = new Genre(UUID.randomUUID(), "Action");
        Optional<Genre> expected = Optional.of(expectedGenre);
        when(genreRepository.findByName("Action"))
                .thenReturn(Optional.of(expectedGenre));
        Optional<Genre> actual = genreService.findByName("Action");
        assertEquals(expected, actual, "Returned wrong genre");
    }
}
