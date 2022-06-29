package com.example.ShoppingCart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    //@Autowired
    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void findAll() {
        List<Product> products = productService.findAll();
        Assertions.assertEquals(10, products.size());
    }

    @Test
    void findById() {
    }

    @Test
    void saveProduct() {
    }

    @Test
    void deleteProduct() {
    }
}