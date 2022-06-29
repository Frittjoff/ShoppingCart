package com.example.ShoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public List<Product> findAll() {
        return (List<Product>)repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id).get();
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }
}
