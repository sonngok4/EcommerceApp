package com.example.ecommerce_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@SpringBootApplication
public class EcommerceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppApplication.class, args);
		System.out.println("Server started at http://localhost:8080/");
	}

	@GetMapping("")
	public String home() {
		return "pages/index";
	}

	@GetMapping("/shop")
	public String shop() {
		return "pages/shop";
	}

	@GetMapping("/about")
	public String about() {
		return "pages/about";
	}

	@GetMapping("/detail")
	public String detail() {
		return "pages/detail";
	}

	@GetMapping("/cart")
	public String cart() {
		return "pages/cart";
	}

	@GetMapping("/checkout")
	public String checkout() {
		return "pages/checkout";
	}

	@GetMapping("/contact")
	public String contact() {
		return "pages/contact";
	}

	@GetMapping("/services")
	public String service() {
		return "pages/services";
	}

	@GetMapping("/blog")
	public String blog() {
		return "pages/blog";
	}

}
