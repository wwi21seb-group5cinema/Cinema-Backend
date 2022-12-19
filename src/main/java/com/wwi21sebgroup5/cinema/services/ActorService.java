package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Actor;
import com.wwi21sebgroup5.cinema.exceptions.ActorAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.ActorRepository;
import com.wwi21sebgroup5.cinema.requestObjects.ActorRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActorService {
    @Autowired
    ActorRepository actorRepository;

    public Actor add(ActorRequestObject actorObject) throws ActorAlreadyExistsException {

        Optional<Actor> foundActor = actorRepository.findByNameAndFirstName(
                actorObject.getName(),
                actorObject.getFirstName());
        if (foundActor.isPresent()) {
            throw new ActorAlreadyExistsException(
                    actorObject.getName(),
                    actorObject.getFirstName());
        }

        Actor a = new Actor(
                actorObject.getName(),
                actorObject.getFirstName());

        actorRepository.save(a);
        return a;
    }


    public List<Actor> findAll() {
        return actorRepository.findAll();

    }

    public Optional<Actor> findById(UUID id) {
        return actorRepository.findById(id);

    }
}
