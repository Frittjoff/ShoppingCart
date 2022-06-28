package com.example.ShoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ShopController {

    @Autowired
    private ProductRepository repository;

    @GetMapping("/products")
    public List<Product> products() {
        return (List<Product>) repository.findAll();
    }

    @GetMapping("/product/{id}")
    public Product product(@PathVariable Long id) {
        return repository.findById(id).get();
    }

}
