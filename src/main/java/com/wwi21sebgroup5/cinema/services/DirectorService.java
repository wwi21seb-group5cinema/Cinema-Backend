package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Director;
import com.wwi21sebgroup5.cinema.exceptions.DirectorAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.DirectorRepository;
import com.wwi21sebgroup5.cinema.repositories.ProducerRepository;
import com.wwi21sebgroup5.cinema.requestObjects.DirectorRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {
    @Autowired
    DirectorRepository directorRepository;
    @Autowired
    ProducerRepository producerRepository;

    public Director add(DirectorRequestObject directorObject) throws DirectorAlreadyExistsException {

        Optional<Director> foundDirector = directorRepository.findByNameAndFirstName(
                directorObject.getName(),
                directorObject.getFirstName());
        if (foundDirector.isPresent()) {
            throw new DirectorAlreadyExistsException(directorObject.getName(), directorObject.getFirstName());
        }
        Director d = new Director(directorObject.getName(), directorObject.getFirstName());
        directorRepository.save(d);
        return d;
    }

    public Optional<Director> findByNameAndFirstName(String name, String firstname) {
        return directorRepository.findByNameAndFirstName(name, firstname);

    }

    public List<Director> findAll() {
        return directorRepository.findAll();

    }
}
