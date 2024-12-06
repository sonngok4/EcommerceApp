package com.example.ecommerce_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_app.dto.ProductDTO;
import com.example.ecommerce_app.dto.ProductRequest;
import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.model.Shop;
import com.example.ecommerce_app.repository.ProductRepository;
import com.example.ecommerce_app.repository.ShopRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ValidationException;

import com.example.ecommerce_app.repository.CategoryRepository;
import com.example.ecommerce_app.exception.ResourceNotFoundException;
import com.example.ecommerce_app.model.User;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @Transactional
    public ProductDTO createProduct(ProductRequest request, User currentUser) {
        // Lấy shop của user hiện tại
        Shop currentShop = shopRepository.findByOwner(currentUser)
                .orElseThrow(() -> new RuntimeException("Shop not found for current user"));

        // Tạo sản phẩm
        Product product = new Product();
        product.setProductName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        // product.setStock(request.getStock());
        product.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        // product.setImageUrl(request.getImageUrl());
        

        // Gán shop cho sản phẩm
        product.setShop(currentShop);

        // Lưu sản phẩm
        Product savedProduct = productRepository.save(product);

        // Chuyển đổi sang DTO
        return convertToDTO(savedProduct);
    }

    private ProductDTO convertToDTO(Product savedProduct) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(savedProduct.getId());
        productDTO.setProductName(savedProduct.getProductName());
        productDTO.setDescription(savedProduct.getDescription());
        productDTO.setPrice(savedProduct.getPrice());
        productDTO.setImageUrl(savedProduct.getImageUrl());
        productDTO.setCategoryName(savedProduct.getCategory().getCategoryName());

        return productDTO;
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
}