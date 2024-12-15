// ImageUploadController.java
package com.example.ecommerce_app.controller.web;

import com.example.ecommerce_app.service.UploadImageService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    private final UploadImageService uploadImageService;

    @Autowired
    public ImageUploadController(UploadImageService uploadImageService) {
        this.uploadImageService = uploadImageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadedImage = uploadImageService.uploadImage(file, "furni-assets");
        return ResponseEntity.ok(uploadedImage);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable String imageId) throws IOException {
        uploadImageService.deleteImageFromCloudinary(imageId);
        return ResponseEntity.noContent().build();
    }
}