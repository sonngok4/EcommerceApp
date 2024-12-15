package com.example.ecommerce_app.controller.web.Admin;

import java.util.List;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_app.dto.ProductRequestDTO;
import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.repository.ProductRepository;
import com.example.ecommerce_app.service.CategoryService;
import com.example.ecommerce_app.service.ProductService;
import com.example.ecommerce_app.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping({ "", "/dashboard" })
    public String admin() {
        return "admin/dashboard/index";
    }

    @GetMapping("/users")
    public String listUsers() {
        return "admin/users/list";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAllByOrderByCreatedAtDesc(); // Sắp xếp theo createdAt giảm dần
        System.out.println("------>controller/admin/products");
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/list";
    }

    // Phương thức hiển thị trang thêm sản phẩm

    @GetMapping("/products/add")
    public String showAddProductPage(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("product", new ProductRequestDTO()); // Tạo đối tượng mới cho form thêm
        return "admin/products/add"; // Trả về view thêm sản phẩm
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        System.out.println("------>controller/admin/products/edit");
        Product product = productService.findById(id);
        System.out.println("Categories: " + categoryService.getAllCategories());
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/edit"; // Trả về view chỉnh sửa sản phẩm
    }

    @DeleteMapping("/products/delete/{id}")
    public String delete_product(@PathVariable("id") Long id) {
        System.out.println("------>controller/admin/products/delete");
        productService.deleteProduct(id);
        return "admin/products/list";
    }

    @GetMapping("/orders")
    public String listOrders() {
        return "admin/orders/list";
    }

    @GetMapping("/reviews")
    public String reviews() {
        return "admin/reviews";
    }

    @GetMapping("/categories")
    public String categories() {
        return "admin/categories";
    }

}
