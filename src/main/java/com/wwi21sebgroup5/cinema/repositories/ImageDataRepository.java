package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.ImageData;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageDataRepository extends CrudRepository<ImageData, UUID> {


    Optional<ImageData> findById(UUID id);
}
