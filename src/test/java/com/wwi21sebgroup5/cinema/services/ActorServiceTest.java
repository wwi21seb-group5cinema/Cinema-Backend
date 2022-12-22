package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Actor;
import com.wwi21sebgroup5.cinema.exceptions.ActorAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.ActorRepository;
import com.wwi21sebgroup5.cinema.requestObjects.ActorRequestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

    @Mock
    ActorRepository actorRepository;

    @InjectMocks
    ActorService actorService;

    private List<Actor> getExpectedActors() {
        Actor firstActor = new Actor("Kevin", "Rieger");
        Actor secondActor = new Actor("Nico", "Niebisch");
        Actor thirdActor = new Actor("Paul", "Bahde");
        return List.of(firstActor, secondActor, thirdActor);
    }

    @Test
    @DisplayName("Test getting all actors")
    public void testGetAllActors() {
        List<Actor> expected = getExpectedActors();
        when(actorRepository.findAll()).thenReturn(expected);
        List<Actor> actual = actorService.findAll();
        assertEquals(expected, actual, "Returned wrong list of actors");
    }

    @Test
    @DisplayName("Test getting actor by Id")
    public void testGetActorByID() {
        Actor expectedActor = new Actor("RIEGER", "NICO");
        expectedActor.setId(UUID.randomUUID());
        Optional<Actor> expected = Optional.of(expectedActor);
        when(actorRepository.findById(expectedActor.getId())).thenReturn(Optional.of(expectedActor));
        Optional<Actor> actual = actorService.findById(expectedActor.getId());
        assertEquals(expected, actual, "Returned wrong actor");
    }

    @Test
    @DisplayName("Test getting actor by Name and Firstname")
    public void testGetActorByNameAndFirstName() {
        Actor expectedActor = new Actor("RIEGER", "NICO");
        Optional<Actor> expected = Optional.of(expectedActor);
        when(actorRepository.findByNameAndFirstName("RIEGER", "NICO"))
                .thenReturn(Optional.of(expectedActor));
        Optional<Actor> actual = actorService.findByNameAndFirstName("RIEGER", "NICO");
        assertEquals(expected, actual, "Returned wrong actor");
    }

    @Test
    @DisplayName("Adding a new Actor successfully")
    public void testAddingNewActorSuccessfully() {
        Actor expected = new Actor("RIEGER", "NICO");
        ActorRequestObject actorRequestObject = new ActorRequestObject("RIEGER", "NICO");
        when(actorRepository.findByNameAndFirstName("RIEGER", "NICO"))
                .thenReturn(Optional.empty());
        Actor actual = null;
        try {
            actual = actorService.add(actorRequestObject);
        } catch (ActorAlreadyExistsException e) {
            fail("Failed");
        }
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding a new Actor unsuccessfully")
    public void testAddingNewActorUnsuccessfully() {
        Actor existing = new Actor("RIEGER", "NICO");
        ActorRequestObject actorRequestObject = new ActorRequestObject("RIEGER", "NICO");
        when(actorRepository.findByNameAndFirstName("RIEGER", "NICO"))
                .thenReturn(Optional.of(existing));
        assertThrows(ActorAlreadyExistsException.class, () -> actorService.add(actorRequestObject));
    }
}
