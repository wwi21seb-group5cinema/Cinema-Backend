package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.config.ImageCompressor;
import com.wwi21sebgroup5.cinema.entities.ImageData;
import com.wwi21sebgroup5.cinema.exceptions.ImageNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.ImageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ImageDataRepository repository;

    public ImageData uploadImage(MultipartFile file) throws IOException, InternalError {

        ImageData imageData = repository.save(new ImageData(
                file.getContentType(),
                ImageCompressor.compressImage(file.getBytes())
        ));
        if (imageData == null) {
            throw new InternalError("Image could not be saved");
        }
        return imageData;
    }

    public ImageData downloadImage(UUID id) throws ImageNotFoundException {
        System.out.println("SERVICE");
        Optional<ImageData> imageData = repository.findById(id);
        System.out.println("REPO");
        if (imageData.isEmpty()) {
            throw new ImageNotFoundException(id);
        }
        return imageData.get();
    }
}
