package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Actor;
import com.wwi21sebgroup5.cinema.exceptions.ActorAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.ActorNotFoundException;
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

    /**
     * @param actorObject which shall be stored in database
     * @return actor which was created
     * @throws ActorAlreadyExistsException if an actor with the same name and firstname already exists
     */
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

    /**
     * @return all actors in form of an list
     */
    public List<Actor> findAll() {
        return actorRepository.findAll();

    }

    /**
     * @param id which actor should be given back
     * @return the actor matching the id in form of an optional
     */
    public Optional<Actor> findById(UUID id) {
        return actorRepository.findById(id);

    }

    /**
     * @param name
     * @param firstName
     * @return the actor matching the given parameters
     * @throws ActorNotFoundException thrown if there is no matching actor
     */
    public Actor findByNameAndFirstName(String name, String firstName) throws ActorNotFoundException {
        Optional<Actor> a = actorRepository.findByNameAndFirstName(name, firstName);
        if (a.isEmpty()) {
            throw new ActorNotFoundException(name, firstName);
        }
        return a.get();
    }
}
