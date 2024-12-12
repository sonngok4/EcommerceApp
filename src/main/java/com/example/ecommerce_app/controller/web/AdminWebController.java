package com.example.ecommerce_app.controller.web;

import java.security.Principal;
import java.util.stream.Collectors;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce_app.dto.ProductRequest;
import com.example.ecommerce_app.service.CategoryService;
import com.example.ecommerce_app.service.ProductService;
import com.example.ecommerce_app.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminWebController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;
    @GetMapping("/dashboard")
    public String admin() {
        return "admin/demo";
    }

    @GetMapping("/accounts")
    public String accounts() {
        return "admin/accounts";
    }

    @GetMapping("/login")
    public String admin_login() {
        return "admin/login";
    }

    @GetMapping("/products")
    public String products() {
        return "admin/products";
    }

    // Phương thức hiển thị trang thêm sản phẩm
    @GetMapping("/add-product")
    public String showAddProductPage(Model model) {
        // Thêm danh sách categories để hiển thị dropdown
        model.addAttribute("categories", categoryService.getAllCategories());
        System.out.println("Categories:" + categoryService.getAllCategories());

        // Tạo đối tượng product mới để binding form
        model.addAttribute("product", new ProductRequest());

        return "demo";
    }

    // Phương thức xử lý submit form
    @PostMapping("/add-product")
    public String addProduct(
            @ModelAttribute("product") @Valid ProductRequest productRequest,
            BindingResult bindingResult,
            Model model,
            Principal principal,
            @RequestParam("imageFile") MultipartFile imageFile) {
        // Kiểm tra validation
        if (bindingResult.hasErrors()) {
            // Nếu có lỗi, trả về lại trang với thông báo lỗi
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/add-product";
        }

        try {
            // Thêm ảnh vào request nếu có
            productRequest.setImageFile(imageFile);

            // Lấy user hiện tại
            // User currentUser = userService.getUserByUsername(principal.getName());

            // Gọi service để tạo sản phẩm
            // productService.createProduct(productRequest, currentUser);

            // Chuyển hướng và thông báo thành công
            return "redirect:/admin/add-product?success";
        } catch (Exception e) {
            // Xử lý ngoại lệ
            model.addAttribute("error", e);
            // model.addAttribute("error", "Có lỗi xảy ra khi thêm sản phẩm");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/add-product";
        }
    }

    @GetMapping("/edit-product")
    public String edit_product() {
        return "admin/edit-product";
    }

    @GetMapping("/orders")
    public String orders() {
        return "admin/orders";
    }

    @GetMapping("/reviews")
    public String reviews() {
        return "admin/reviews";
    }

    @GetMapping("/users")
    public String users() {
        return "admin/users";
    }

    @GetMapping("/categories")
    public String categories() {
        return "admin/categories";
    }

}
