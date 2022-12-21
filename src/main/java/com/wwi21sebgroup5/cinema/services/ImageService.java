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

    /**
     * @param file
     * @return the stored ImageData Object (with its UUID)
     * @throws IOException
     * @throws InternalError
     */
    public ImageData uploadImage(MultipartFile file) throws IOException, InternalError {
        // In the moment the method compressImage() doesn´t do anything
        ImageData imageData = repository.save(new ImageData(
                file.getContentType(),
                ImageCompressor.compressImage(file.getBytes())
        ));
        if (imageData == null) {
            throw new InternalError("Image could not be saved");
        }
        return imageData;
    }

    /**
     * @param id
     * @return the ImageData object with the matching id
     * @throws ImageNotFoundException
     */
    public ImageData downloadImage(UUID id) throws ImageNotFoundException {
        Optional<ImageData> imageData = repository.findById(id);
        if (imageData.isEmpty()) {
            throw new ImageNotFoundException(id);
        }
        return imageData.get();
    }
}
