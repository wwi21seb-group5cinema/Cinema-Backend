package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.repositories.ActorRepository;
import com.wwi21sebgroup5.cinema.requestObjects.ActorRequestObject;
import com.wwi21sebgroup5.cinema.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/v1/actor")
public class ActorController {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    ActorService actorService;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(){
        return new ResponseEntity<>(actorRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Object> putAll(@RequestBody ActorRequestObject actorObject)
    { try
        {
            System.out.println("IN TRY");
            actorService.add(actorObject);
        }
        catch(Exception e){
            System.out.println("EXeption");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
        System.out.println("return");
        return new ResponseEntity<>("new ACTOR",HttpStatus.OK);
    }
}
