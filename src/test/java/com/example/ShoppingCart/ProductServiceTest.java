package com.example.ShoppingCart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @BeforeEach
    void setUp() {
        //productService = new ProductService();
    }

    @Test
    void findAll() {
        List<Product> products = productService.findAll();
        Assertions.assertEquals(10, products.size());
    }

    @Test
    void findById() {
        Long id = 5L;
        String expected = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sit amet molestie ligula. Integer at nulla luctus, vehicula augue ut, semper tortor. Pellentesque eu luctus nisi.";
        Product product = productService.findById(id);
        Assertions.assertEquals(expected, product.getDescription());
    }

    @Test
    void saveProduct() {

        String description = "New Item Description";
        Product product = new Product(11L, "New Item", 126, 12, description);

        List<Product> products = productService.findAll();
        Assertions.assertEquals(10, products.size());

        productService.saveProduct(product);

        products = productService.findAll();
        Assertions.assertEquals(11, products.size());

        Assertions.assertEquals(description, products.get(10).getDescription());

    }

    @Test
    void deleteProduct() {

        List<Product> products = productService.findAll();
        int productsSize = products.size();

        Product product = productService.findById(7L);
        productService.deleteProduct(product);

        products = productService.findAll();
        Assertions.assertEquals(productsSize -1, products.size());

    }
}