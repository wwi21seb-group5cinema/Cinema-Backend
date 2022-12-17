package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Producer;
import com.wwi21sebgroup5.cinema.exceptions.ProducerAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.ProducerRepository;
import com.wwi21sebgroup5.cinema.requestObjects.ProducerRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProducerService {
    @Autowired
    ProducerRepository producerRepository;

    public Producer add(ProducerRequestObject producerObject) throws ProducerAlreadyExistsException {

        Optional<Producer> foundProducer = producerRepository.findByName(
                producerObject.getName());
        if (foundProducer.isPresent()) {
            throw new ProducerAlreadyExistsException(producerObject.getName());
        }
        Producer p = new Producer(producerObject.getName());
        producerRepository.save(p);
        return p;
    }

    public Optional<Producer> findByName(String name) {
        return producerRepository.findByName(name);

    }

    public List<Producer> findAll() {
        return producerRepository.findAll();

    }
}
