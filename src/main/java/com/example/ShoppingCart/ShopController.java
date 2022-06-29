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
    ProductService productService;

    @Autowired
    AdminRepository admRepo;

    @GetMapping("/products")
    public String products(Model model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        List<Product> products = (List<Product>) productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String product(Model model, @PathVariable Long id) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/product")
    public String addProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "updateProduct";
    }
    @GetMapping("/login")
    public String loginAdmin() {
        return "login";
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

    @GetMapping("/logout")
    public String logOutAdmin(HttpSession session) {
        session.invalidate();
        return "products";
    }




    @GetMapping("/products/add")
    public String productUpdate(Model model) {

        model.addAttribute("product", new Product());
        return "updateProduct";
    }
}
