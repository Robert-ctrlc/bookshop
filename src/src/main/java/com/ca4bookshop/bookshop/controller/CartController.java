package com.ca4bookshop.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ca4bookshop.bookshop.model.CartItem;
import com.ca4bookshop.bookshop.repository.CartService;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;  

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems(); 
        model.addAttribute("cart", cartItems);
        return "cart-view";  
    }

    @PostMapping("/cart/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, @RequestParam int quantity) {
        cartService.addItemToCart(bookId, quantity);
        return "redirect:/cart";  
    }

    @PostMapping("/cart/remove/{bookId}")
    public String removeFromCart(@PathVariable Long bookId) {
        cartService.removeItemFromCart(bookId);
        return "redirect:/cart"; 
    }

    @GetMapping("/checkout")
    public String checkout() {
        cartService.checkout(); 
        return "redirect:/order-history";  
    }
}
