package com.example.ecommerce_app.controller.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductWebController {

    private final ProductService productService;

    // Constructor injection
    public ProductWebController(ProductService productService) {
        this.productService = productService;
    }

    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        // Page<Product> products = productService.findPaginated(page, size);
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        System.out.printf("------>controller/products/listProducts", products);
        return "pages/shop"; // Trả về template Thymeleaf
    }

    // Hiển thị chi tiết sản phẩm
    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "products/detail";
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/form";
    }

    // Xử lý submit form thêm/sửa sản phẩm
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute Product product,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "products/form";
        }
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Product saved successfully!");
        return "redirect:/products";
    }
}