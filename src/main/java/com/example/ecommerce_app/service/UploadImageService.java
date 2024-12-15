// ImageUploadService.java
package com.example.ecommerce_app.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadImageService {

    private final Cloudinary cloudinary;

    @Autowired
    public UploadImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // Phương thức upload ảnh lên Cloudinary và trả về URL của ảnh
    public String uploadImage(MultipartFile file, String folder) throws IOException {
        // Kiểm tra nếu không có file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("No image file provided");
        }

        // Tải ảnh lên Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder", folder, "overwrite", true));

        // Trả về URL của ảnh
        return (String) uploadResult.get("secure_url");
    }

    // Phương thức xóa ảnh khỏi Cloudinary
    public void deleteImageFromCloudinary(String publicId) throws IOException {
        if (publicId == null || publicId.isEmpty()) {
            throw new IllegalArgumentException("Public ID must not be null or empty");
        }

        // Xóa ảnh từ Cloudinary bằng public_id
        // Xóa ảnh và vô hiệu hóa trên CDN
        Map<String, Object> options = new HashMap<>();
        options.put("invalidate", true);
        cloudinary.uploader().destroy(publicId, options);
        // System.out.println("Image deleted successfully from Cloudinary: " + publicId);
    }

    // Hàm trích xuất public_id từ URL Cloudinary (giống như ví dụ trước)
public String extractPublicIdFromUrl(String imageUrl) {
    // Tách URL tại vị trí "/upload/" để lấy phần sau đó
    String[] urlParts = imageUrl.split("/upload/");
    if (urlParts.length > 1) {
        // Lấy phần sau "/upload/" và trước dấu "?" (nếu có)
        String publicIdWithExtension = urlParts[1].split("\\?")[0];
        
        // Trả về publicId mà không tách phần phiên bản (v1734286944)
        String publicId = publicIdWithExtension.substring(publicIdWithExtension.indexOf("/") + 1);

        // Trả về publicId sau khi xóa phần phiên bản (v1734286944)
        publicId = publicId.substring(0, publicId.indexOf("."));
        
        // In ra publicId để kiểm tra
        // System.out.println("Public ID: " + publicId);
        return publicId;
    }
    return null;
}


}
