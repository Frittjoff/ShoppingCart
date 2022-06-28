package com.example.ShoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
        Product product = repository.findById(id);
        model.addAttribute("product", product);
        return "product";
    }


}
