package com.wwi21sebgroup5.cinema.services;


import com.wwi21sebgroup5.cinema.entities.Producer;
import com.wwi21sebgroup5.cinema.exceptions.ProducerAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.ProducerRepository;
import com.wwi21sebgroup5.cinema.requestObjects.ProducerRequestObject;
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

public class ProducerServiceTest {

    @Mock
    ProducerRepository producerRepository;

    @InjectMocks
    ProducerService producerService;

    private List<Producer> getExpectedProducers() {
        Producer firstProducer = new Producer("Kevin");
        Producer secondProducer = new Producer("Nico");
        Producer thirdProducer = new Producer("Paul");
        return List.of(firstProducer, secondProducer, thirdProducer);
    }

    @Test
    @DisplayName("Test getting all producers")
    public void testGetAllProducers() {
        List<Producer> expected = getExpectedProducers();
        when(producerRepository.findAll()).thenReturn(expected);
        List<Producer> actual = producerService.findAll();
        assertEquals(expected, actual, "Returned wrong list of producers");
    }


    @Test
    @DisplayName("Test getting producer by Name ")
    public void testGetProducerByName() {
        Producer expectedProducer = new Producer("RIEGER");
        Optional<Producer> expected = Optional.of(expectedProducer);
        when(producerRepository.findByName("RIEGER"))
                .thenReturn(Optional.of(expectedProducer));
        Optional<Producer> actual = producerService.findByName("RIEGER");
        assertEquals(expected, actual, "Returned wrong producer");
    }

    @Test
    @DisplayName("Adding a new Producer successfully")
    public void testAddingNewProducerSuccessfully() {
        Producer expected = new Producer("RIEGER");
        ProducerRequestObject producerRequestObject = new ProducerRequestObject("RIEGER");
        when(producerRepository.findByName("RIEGER"))
                .thenReturn(Optional.empty());
        Producer actual = null;
        try {
            actual = producerService.add(producerRequestObject);
        } catch (ProducerAlreadyExistsException e) {
            fail("Failed");
        }
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Adding a new Producer unsuccessfully")
    public void testAddingNewProducerUnsuccessfully() {
        Producer existing = new Producer("RIEGER");
        ProducerRequestObject producerRequestObject = new ProducerRequestObject("RIEGER");
        when(producerRepository.findByName("RIEGER"))
                .thenReturn(Optional.of(existing));
        assertThrows(ProducerAlreadyExistsException.class, () -> producerService.add(producerRequestObject));
    }


}

