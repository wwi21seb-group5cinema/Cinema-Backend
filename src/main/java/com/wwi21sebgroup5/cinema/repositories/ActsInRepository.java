package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.ActsIn;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ActsInRepository extends CrudRepository<ActsIn, UUID> {

}
