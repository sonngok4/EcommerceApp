// ImageValidator.java
package com.example.ecommerce_app.utils;

import com.example.ecommerce_app.exception.ImageUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class ImageValidator {
    @Value("${upload.max-file-size}")
    private long maxFileSize;

    @Value("${upload.allowed-content-types}")
    private List<String> allowedContentTypes;

    public void validate(MultipartFile file) {
        // Check if file is empty
        if (file == null || file.isEmpty()) {
            throw new ImageUploadException("File is empty");
        }

        // Check file size
        if (file.getSize() > maxFileSize) {
            throw new ImageUploadException("File is too large. Maximum size is " + maxFileSize + " bytes");
        }

        // Check content type
        String contentType = file.getContentType();
        if (contentType == null || !allowedContentTypes.contains(contentType)) {
            throw new ImageUploadException(
                    "Invalid file type. Allowed types are: " + String.join(", ", allowedContentTypes));
        }
    }
}