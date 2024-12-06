// ImageUploadController.java
package com.example.ecommerce_app.controller.web;

import com.example.ecommerce_app.service.ImageUploadService;
import com.example.ecommerce_app.model.ImageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageMetadata> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageMetadata uploadedImage = imageUploadService.uploadImage(file);
        return ResponseEntity.ok(uploadedImage);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageUploadService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }
}