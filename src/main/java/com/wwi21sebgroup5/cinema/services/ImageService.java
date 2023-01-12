package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.ImageData;
import com.wwi21sebgroup5.cinema.exceptions.ImageCouldNotBeCompressedException;
import com.wwi21sebgroup5.cinema.exceptions.ImageNotFoundException;
import com.wwi21sebgroup5.cinema.helper.ImageCompressor;
import com.wwi21sebgroup5.cinema.repositories.ImageDataRepository;
import com.wwi21sebgroup5.cinema.requestObjects.AddImageReturnObject;
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
    public AddImageReturnObject uploadImage(MultipartFile file) throws IOException, InternalError, ImageCouldNotBeCompressedException {
        byte[] bytes = ImageCompressor.compressImage(file.getBytes(), 1, file.getContentType());
        boolean compressed = (file.getBytes().length != bytes.length);
        ImageData imageData = repository.save(new ImageData(
                file.getContentType(),
                bytes,
                compressed
        ));
        return new AddImageReturnObject(imageData.getId(), imageData.getType(), imageData.isCompressed());
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
