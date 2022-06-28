package com.example.ShoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShopController {


    @Autowired
    ProductRepository repository;

    @Autowired
    AdminRepository admRepo;

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
    @GetMapping("/login")
    public String loginAdmin() {
        return "loginAdmin";
    }

    @PostMapping("/login")
    public String loginAdminPost(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Admin isAdmin = admRepo.getAdmin(username, password);
        if (isAdmin != null) {
            session.setAttribute("admin", isAdmin);
            return "products";
        } else
            return "redirect:/login";
    }




    @GetMapping("/products/add")
    public String productUpdate(Model model) {

        model.addAttribute("product", new Product());
        return "updateProduct";
    }
}
