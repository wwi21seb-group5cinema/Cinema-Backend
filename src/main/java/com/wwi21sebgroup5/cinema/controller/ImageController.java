package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.config.ImageCompressor;
import com.wwi21sebgroup5.cinema.entities.ImageData;
import com.wwi21sebgroup5.cinema.exceptions.ImageNotFoundException;
import com.wwi21sebgroup5.cinema.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping(path = "/v1/image")
public class ImageController {
    @Autowired
    private ImageService service;


    /**
     * @param file (Multipart file - Content-Type should be an image-format)
     * @return the newly created image-object
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam("image") MultipartFile file) {
        try {
            ImageData uploadImage = service.uploadImage(file);
            return new ResponseEntity<>(uploadImage, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param id
     * @return the Image with the matching ID in the appropriate format
     */
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable UUID id) {
        try {
            ImageData imageData = service.downloadImage(id);
            // In the Moment decompress Image doesn't do anything
            byte[] bytes = ImageCompressor.decompressImage(imageData.getImageData());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(imageData.getType()))
                    .body(bytes);

        } catch (ImageNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
