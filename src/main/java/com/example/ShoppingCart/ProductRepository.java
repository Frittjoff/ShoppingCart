package com.example.ShoppingCart;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Long, Product> {

    Product findById(Long id);
}
