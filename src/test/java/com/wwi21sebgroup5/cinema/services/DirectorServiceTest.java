package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Director;
import com.wwi21sebgroup5.cinema.exceptions.DirectorAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.DirectorRepository;
import com.wwi21sebgroup5.cinema.requestObjects.DirectorRequestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceTest {

    @Mock
    DirectorRepository directorRepository;

    @InjectMocks
    DirectorService directorService;

    private List<Director> getExpectedDirectors() {
        Director firstDirector = new Director("Kevin", "Rieger");
        Director secondDirector = new Director("Nico", "Niebisch");
        Director thirdDirector = new Director("Paul", "Bahde");
        return List.of(firstDirector, secondDirector, thirdDirector);
    }

    @Test
    @DisplayName("Test getting all directors")
    public void testGetAllDirectors() {
        List<Director> expected = getExpectedDirectors();
        when(directorRepository.findAll()).thenReturn(expected);
        List<Director> actual = directorService.findAll();
        assertEquals(expected, actual, "Returned wrong list of directors");
    }


    @Test
    @DisplayName("Test getting director by Name and Firstname")
    public void testGetDirectorByNameAndFirstName() {
        Director expectedDirector = new Director("RIEGER", "NICO");
        Optional<Director> expected = Optional.of(expectedDirector);
        when(directorRepository.findByNameAndFirstName("RIEGER", "NICO"))
                .thenReturn(Optional.of(expectedDirector));
        Optional<Director> actual = directorService.findByNameAndFirstName("RIEGER", "NICO");
        assertEquals(expected, actual, "Returned wrong director");
    }

    @Test
    @DisplayName("Adding a new Director successfully")
    public void testAddingNewDirectorSuccessfully() {
        Director expected = new Director("RIEGER", "NICO");
        DirectorRequestObject directorRequestObject = new DirectorRequestObject("RIEGER", "NICO");
        when(directorRepository.findByNameAndFirstName("RIEGER", "NICO"))
                .thenReturn(Optional.empty());
        Director actual = null;
        try {
            actual = directorService.add(directorRequestObject);
        } catch (DirectorAlreadyExistsException e) {
            fail("Failed");
        }
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding a new Director unsuccessfully")
    public void testAddingNewDirectorUnsuccessfully() {
        Director existing = new Director("RIEGER", "NICO");
        DirectorRequestObject directorRequestObject = new DirectorRequestObject("RIEGER", "NICO");
        when(directorRepository.findByNameAndFirstName("RIEGER", "NICO"))
                .thenReturn(Optional.of(existing));
        assertThrows(DirectorAlreadyExistsException.class, () -> directorService.add(directorRequestObject));
    }
}

