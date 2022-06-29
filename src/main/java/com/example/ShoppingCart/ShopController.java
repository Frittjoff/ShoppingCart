package com.example.ShoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
        List<Product> products = productService.findAll();
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
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
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
            return "redirect:/admin";
            //return "products";        //todo vilken template kör vi?
        } else
            return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logOutAdmin(HttpSession session) {
        session.invalidate();
        return "redirect:/admin";
    }


    @GetMapping("/products/add")
    public String productUpdate(Model model) {

        model.addAttribute("product", new Product());
        return "updateProduct";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.findById(id));
        return "updateProduct";
    }

    @GetMapping("/delete/{id}")
    public String delete(RestTemplate restTemplate, @PathVariable Long id) {
        Product product = productService.findById(id);
        productService.deleteProduct(product);

        return "redirect:/admin";
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(HttpSession session, @PathVariable Long id) {

        Integer sum;
        Product product = productService.findById(id);

        List<Product> cart = (List<Product>)session.getAttribute("cart");
        if(cart == null){
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        cart.add(product);

        sum = (int)session.getAttribute("sum");
        sum += product.getPrice();
        session.setAttribute("sum", sum);

        product.setQuantity(product.getQuantity()-1);

        if(product.getQuantity() < 1) {
            productService.deleteProduct(product);
        }

        return "redirect:/admin";
    }
}
