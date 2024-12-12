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

    @Transactional
    public ProductResponseDTO createProduct(@Valid ProductRequestDTO productDTO, User currentUser) throws IOException {
        // Lấy shop của user hiện tại
        Shop currentShop = shopRepository.findByOwner(currentUser)
                .orElseThrow(() -> new RuntimeException("Shop not found for current user"));
        System.out.println("Shop: " + currentShop);

        // Upload ảnh lên Cloudinary
        Map uploadResult = cloudinary.uploader().upload(
                productDTO.getImage().getBytes(),
                ObjectUtils.asMap(
                        "folder", "furni-products/",
                        "overwrite", true));

        // Lấy URL ảnh từ kết quả upload
        String imageUrl = (String) uploadResult.get("secure_url");

        // Tạo sản phẩm
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setImageUrl(imageUrl);
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId())
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
        productRepository.deleteById(id);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) throws IOException {
        if (productRequestDTO.getId() == null) {
            throw new IllegalArgumentException("The given id must not be null!!");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String imageUrl = product.getImageUrl();
        if (productRequestDTO.getImage() != null) {
            Map uploadResult = cloudinary.uploader().upload(
                    productRequestDTO.getImage().getBytes(),
                    ObjectUtils.asMap(
                            "folder", "furni-products/",
                            "overwrite", true));
            imageUrl = (String) uploadResult.get("secure_url");
        }
        product.setId(id);
        product.setImageUrl(imageUrl);
        product.setProductName(productRequestDTO.getProductName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setStockQuantity(productRequestDTO.getStockQuantity());
        product.setCategory(categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }
}