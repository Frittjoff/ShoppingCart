package com.example.ShoppingCart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShoppingCartApplicationTests {

	@Autowired
	ProductRepository prodRepo;
	@Autowired
	AdminRepository admRepo;

	@Test
	void contextLoads() {
	}

	@Test
	public void testFindAllProducts() {
		List<Product> products = (List<Product>) prodRepo.findAll();
		Assertions.assertEquals(10, products.size());
	}

	@Test
	public void adminTest() {
		Admin admin = admRepo.getAdmin("admin", "admin123");
		Assertions.assertEquals("admin", admin.getUsername());
	}

}
