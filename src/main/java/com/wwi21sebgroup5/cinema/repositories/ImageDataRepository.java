package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageDataRepository extends JpaRepository<ImageData, UUID> {


    Optional<ImageData> findById(UUID id);
}
