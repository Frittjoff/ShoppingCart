package com.example.ShoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ShopController {


    @Autowired
    private ProductRepository repository;

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = (List)repository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String product(Model model, @PathVariable Long id) {
        Product product = repository.findById(id).get();
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/product")
    public String addProduct(@ModelAttribute Product product) {
        repository.save(product);
        return "product";
    }

    @GetMapping("/products/add")
    public String productUpdate(Model model) {

        model.addAttribute("product", new Product());
        return "updateProduct";
    }
}
