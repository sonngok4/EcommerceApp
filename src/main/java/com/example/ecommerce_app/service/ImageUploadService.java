// ImageUploadService.java
package com.example.ecommerce_app.service;

import com.example.ecommerce_app.exception.ImageUploadException;
import com.example.ecommerce_app.repository.ImageRepository;
import com.example.ecommerce_app.model.ImageMetadata;
import com.example.ecommerce_app.utils.ImageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageUploadService {

    private final ImageRepository imageRepository;
    private final ImageValidator imageValidator;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public ImageUploadService(ImageRepository imageRepository, ImageValidator imageValidator) {
        this.imageRepository = imageRepository;
        this.imageValidator = imageValidator;
    }

    /**
     * Uploads an image file and saves its metadata
     * 
     * @param file MultipartFile to be uploaded
     * @return ImageMetadata of the uploaded image
     * @throws ImageUploadException if upload fails
     */
    public ImageMetadata uploadImage(MultipartFile file) {
        // Validate the image
        imageValidator.validate(file);

        try {
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Create upload directory if it doesn't exist
            Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
            Files.createDirectories(uploadDir);

            // Save file to disk
            Path targetLocation = uploadDir.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Create and save image metadata
            ImageMetadata imageMetadata = new ImageMetadata();
            imageMetadata.setFileName(uniqueFilename);
            imageMetadata.setOriginalFileName(originalFilename);
            imageMetadata.setFileSize(file.getSize());
            imageMetadata.setContentType(file.getContentType());
            imageMetadata.setFilePath(targetLocation.toString());

            return imageRepository.save(imageMetadata);
        } catch (IOException ex) {
            throw new ImageUploadException("Could not store file", ex);
        }
    }

    /**
     * Deletes an image by its ID
     * 
     * @param imageId ID of the image to be deleted
     */
    public void deleteImage(Long imageId) {
        ImageMetadata image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageUploadException("Image not found with id: " + imageId));

        try {
            // Delete file from filesystem
            Files.deleteIfExists(Paths.get(image.getFilePath()));

            // Delete metadata from database
            imageRepository.delete(image);
        } catch (IOException ex) {
            throw new ImageUploadException("Could not delete file", ex);
        }
    }
}