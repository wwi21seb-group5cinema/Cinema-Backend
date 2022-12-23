package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Actor;
import com.wwi21sebgroup5.cinema.exceptions.ActorAlreadyExistsException;
import com.wwi21sebgroup5.cinema.requestObjects.ActorRequestObject;
import com.wwi21sebgroup5.cinema.services.ActorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorControllerTest {

    @Mock
    ActorService actorService;

    @InjectMocks
    ActorController actorController;

    @Test
    @DisplayName("Test successfully getting all actors")
    public void testGetAllActorsSuccessful() {
        Actor firstActor = new Actor("Kevin", "Rieger");
        Actor secondActor = new Actor("Nico", "Niebisch");
        Actor thirdActor = new Actor("Paul", "Bahde");
        List<Actor> expectedActors = List.of(firstActor, secondActor, thirdActor);

        when(actorService.findAll()).thenReturn(expectedActors);

        ResponseEntity<List<Actor>> response = actorController.getAll();

        assertAll(
                "Validating correct response from controller...",
                () -> assertEquals(response.getBody(), expectedActors),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }

    @Test
    @DisplayName("Test unsuccessfully getting all actors")
    public void testGetAllActorsNotSuccessful() {
        when(actorService.findAll()).thenReturn(List.of());

        ResponseEntity<List<Actor>> response = actorController.getAll();

        assertAll(
                "Validating correct response from controller...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND)
        );
    }

    @Test
    @DisplayName("Test successfully getting actor by name and first name")
    public void testGetActorByNameSuccessful() {
        Actor expectedActor = new Actor("Nibisch", "Kevin");

        when(actorService.findByNameAndFirstName("Nibisch", "Kevin"))
                .thenReturn(Optional.of(expectedActor));

        ResponseEntity<Actor> response = actorController.getActorByName("Nibisch", "Kevin");

        assertAll(
                "Validating correct response from controller...",
                () -> assertEquals(response.getBody(), expectedActor),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }

    @Test
    @DisplayName("Test unsuccessfully getting Actors by name")
    public void testGetActorByNameNotSuccessful() {
        when(actorService.findByNameAndFirstName("Nibisch", "Kevin"))
                .thenReturn(Optional.empty());

        ResponseEntity<Actor> response = actorController.getActorByName("Nibisch", "Kevin");

        assertAll(
                "Validating correct response from controller...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND)
        );
    }

    @Test
    @DisplayName("Test add new actor successfully")
    public void testSuccessfulAddedNewActor() {
        Actor expectedActor = new Actor("Nibisch", "Kevin");
        ActorRequestObject actorRequestObject = new ActorRequestObject("Niebisch", "Kevin");

        try {
            when(actorService.add(actorRequestObject)).thenReturn(expectedActor);
        } catch (ActorAlreadyExistsException e) {
            fail("Adding failed");
        }

        ResponseEntity<Object> response = actorController.add(actorRequestObject);

        assertAll(
                "Validating response ...",
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals(expectedActor, response.getBody())
        );
    }

    @Test
    @DisplayName("Test add new actor unsuccessfully")
    public void testUnsuccessfullyAddedNewActor() {
        ActorRequestObject actorRequestObject = new ActorRequestObject("Niebisch", "Kevin");
        try {
            when(actorService.add(actorRequestObject)).thenThrow(new ActorAlreadyExistsException("Nibisch", "Kevin"));
        } catch (ActorAlreadyExistsException e) {
            fail("Adding failed");
        }

        ResponseEntity<Object> response = actorController.add(actorRequestObject);

        assertAll(
                "Validating response ...",
                () -> assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode()),
                () -> assertEquals(response.getBody(), "Actor with the name Nibisch and the firstname Kevin already exists")
        );
    }

}


