package com.example.ShoppingCart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ShoppingCartApplicationTests {

	@Autowired
	ProductRepository prodRepo;
	@Autowired
	AdminRepository admRepo;
	@Autowired
	MockMvc mvc;

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
		Admin notAdmin = admRepo.getAdmin("admin", "123");
		Assertions.assertEquals("admin", admin.getUsername());
		Assertions.assertNull(notAdmin);
	}

	@Test
	public void testAddProduct() {
		Product product = new Product(11L, "tröja", 129, 3, "dmslkgnsl");
		prodRepo.save(product);
		List<Product> products2 = (List<Product>) prodRepo.findAll();
		prodRepo.save(product);
		Assertions.assertEquals(11, products2.size());
	}

	@Test
	void testMainPageGet() throws Exception {

		mvc.perform(
						MockMvcRequestBuilders.get("/Produkter")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Kia Sportage")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("Ford Puma")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("Volvo S90")));
	}





}
