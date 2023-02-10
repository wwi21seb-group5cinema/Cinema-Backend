package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.repositories.MovieRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TmdbServiceTest {

    @Mock
    private ActorService actorService;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private TmdbService tmdbService;

}
