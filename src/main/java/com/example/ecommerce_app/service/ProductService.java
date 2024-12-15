package com.example.ecommerce_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ecommerce_app.dto.ProductRequestDTO;
import com.example.ecommerce_app.dto.ProductResponseDTO;
import com.example.ecommerce_app.dto.CategoryResponseDTO;
import com.example.ecommerce_app.dto.ProductRequest;
import com.example.ecommerce_app.model.Category;
import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.model.Shop;
import com.example.ecommerce_app.repository.ProductRepository;
import com.example.ecommerce_app.repository.ShopRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

import com.example.ecommerce_app.repository.CategoryRepository;
import com.example.ecommerce_app.exception.ResourceNotFoundException;
import com.example.ecommerce_app.model.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UploadImageService uploadImageService;

    @Transactional
    public ProductResponseDTO createProduct(@Valid ProductRequestDTO productDTO, User currentUser) throws IOException {
        // Lấy shop của user hiện tại
        Shop currentShop = shopRepository.findByOwner(currentUser)
                .orElseThrow(() -> new RuntimeException("Shop not found for current user"));
        System.out.println("Shop: " + currentShop);

        // Upload ảnh lên Cloudinary
        String imageUrl = uploadImageService.uploadImage(productDTO.getImageUrl(), "furni-products");
        // Tạo sản phẩm
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setImageUrl(imageUrl);
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryRepository.findById(productDTO.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found")));

        // Gán shop cho sản phẩm
        product.setShop(currentShop);

        // Lưu sản phẩm
        Product savedProduct = productRepository.save(product);

        return convertToDTO(savedProduct);
    }

    private ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO productDTO = new ProductResponseDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getProductName());
        productDTO.setImage(product.getImageUrl());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setDescription(product.getDescription());
        // Convert Category to CategoryResponseDTO and set it
        CategoryResponseDTO categoryDTO = convertCategoryToDTO(product.getCategory());
        productDTO.setCategory(categoryDTO);
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        return productDTO;
    }

    private CategoryResponseDTO convertCategoryToDTO(Category category) {
        CategoryResponseDTO categoryDTO = new CategoryResponseDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getCategoryName()); // Assuming Category has a 'getCategoryName' method.
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setCreatedAt(category.getCreatedAt());
        categoryDTO.setUpdatedAt(category.getUpdatedAt());
        return categoryDTO;
    }

    private void validateProductRequest(ProductRequest request) {
        // Các quy tắc kiểm tra:
        // - Tên sản phẩm không được trống
        // - Giá phải lớn hơn 0
        // - Danh mục tồn tại
        if (StringUtils.isEmpty(request.getName())) {
            throw new ValidationException("Product name cannot be empty");
        }
        // Các validation khác...
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findLimit(PageRequest pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public boolean isProductNameUnique(String productName) {
        return productRepository.findByProductName(productName) == null;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findPaginated(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        return productRepository.findAll(pageRequest);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        String imageUrl = product.getImageUrl();
        if (imageUrl != null) {
            try {
                String publicId = uploadImageService.extractPublicIdFromUrl(imageUrl);
                uploadImageService.deleteImageFromCloudinary(publicId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) throws IOException {
        if (productRequestDTO.getId() == null) {
            throw new IllegalArgumentException("The given id must not be null!!");
        }

        // Lấy sản phẩm hiện tại từ database
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Cập nhật tên sản phẩm nếu có thay đổi
        if (productRequestDTO.getProductName() != null
                && !productRequestDTO.getProductName().equals(product.getProductName())) {
            product.setProductName(productRequestDTO.getProductName());
        }

        // Cập nhật mô tả nếu có thay đổi
        if (productRequestDTO.getDescription() != null
                && !productRequestDTO.getDescription().equals(product.getDescription())) {
            product.setDescription(productRequestDTO.getDescription());
        }

        // Cập nhật giá nếu có thay đổi
        if (productRequestDTO.getPrice() != null && !productRequestDTO.getPrice().equals(product.getPrice())) {
            product.setPrice(productRequestDTO.getPrice());
        }

        // Cập nhật số lượng tồn kho nếu có thay đổi
        if (productRequestDTO.getStockQuantity() != null
                && !productRequestDTO.getStockQuantity().equals(product.getStockQuantity())) {
            product.setStockQuantity(productRequestDTO.getStockQuantity());
        }

        // Cập nhật danh mục nếu có thay đổi
        if (productRequestDTO.getCategory() != null
                && !productRequestDTO.getCategory().equals(product.getCategory().getId())) {
            product.setCategory(categoryRepository.findById(productRequestDTO.getCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        }

        // Cập nhật hình ảnh nếu có thay đổi
        String imageUrl = product.getImageUrl();
        if (productRequestDTO.getImageUrl() != null && !productRequestDTO.getImageUrl().isEmpty()) {
            // Lấy public_id của ảnh cũ để xóa khỏi Cloudinary (nếu có)
            if (imageUrl != null) {
                String publicId = uploadImageService.extractPublicIdFromUrl(product.getImageUrl());
                // System.out.println("Public ID: " + publicId);
                uploadImageService.deleteImageFromCloudinary(publicId); // Xóa ảnh cũ
            }
        
            // Upload ảnh lên Cloudinary
            imageUrl = (String) uploadImageService.uploadImage(productRequestDTO.getImageUrl(), "furni-products/");
        }
        product.setImageUrl(imageUrl);

        // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu
        Product updatedProduct = productRepository.save(product);

        return convertToDTO(updatedProduct);
    }

}