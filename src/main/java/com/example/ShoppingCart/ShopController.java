package com.example.ShoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class ShopController {


    @Autowired
    ProductService productService;

    @Autowired
    AdminRepository admRepo;

    @GetMapping("/products")
    public String products(Model model, HttpSession session) {
        Admin  admin = (Admin) session.getAttribute("admin");
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/Produkter")
    public String product(Model model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "Produkter";
    }



    @GetMapping("/product/{id}")
    public String product(Model model, @PathVariable Long id) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "ProductDetails";
    }



    @PostMapping("/product")
    public String addProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session) {
        List<Product> cart = (List<Product>)session.getAttribute("cart");
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("cart", cart);
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

    @GetMapping("/deleteItem/{id}")
    public String deleteCartItem( @PathVariable Long id, HttpSession session) {
        List<Product> cart = (List<Product>)session.getAttribute("cart");
        if(cart != null){

            for (Product item:cart) {
                int pPrice=0,iSum=0;
                if(item.getId()==id) {
                    pPrice = item.getPrice().intValue();
                    cart.remove(item);
                    session.setAttribute("cart", cart);
                }
                if(cart.size()==0){
                    session.setAttribute("sum", cart.size());
                    session.removeAttribute("cart");
                }else {
                    iSum = Integer.parseInt(session.getAttribute("sum").toString());
                    session.setAttribute("sum", iSum-pPrice);
                }
                return "redirect:/Produkter";
            }
        }
        return "redirect:/Produkter";
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

        //todo summan av alla items

        sum = (Integer)session.getAttribute("sum");
        if (sum == null) {
            sum = 0;
        }
        sum += product.getPrice();
        session.setAttribute("sum", sum);

        // todo quantity minskar
        product.setQuantity(product.getQuantity()-1);
        productService.saveProduct(product);
        if(product.getQuantity() < 1) {
            productService.deleteProduct(product);
        }

        return "redirect:/Produkter";
    }

    @GetMapping("/deleteItem/{id}")
    public String deleteItem(HttpSession session, @PathVariable Long id) {
        List<Product> cart = (List<Product>)session.getAttribute("cart");
        Integer sum;
        Product product = productService.findById(id);

        cart.removeIf(item -> item.getId().equals(id));

        //summan minskar om vi tar bort ett item

        sum = (Integer)session.getAttribute("sum");
        if (sum == null) {
            sum = 0;
        }
        sum -= product.getPrice();
        session.setAttribute("sum", sum);

        //quantity ökar
        product.setQuantity(product.getQuantity()+1);
        productService.saveProduct(product);

        return "redirect:/admin";
    }
}
