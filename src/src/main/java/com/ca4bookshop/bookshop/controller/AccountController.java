package com.ca4bookshop.bookshop.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.ca4bookshop.bookshop.model.Order;
import com.ca4bookshop.bookshop.model.User;
import com.ca4bookshop.bookshop.repository.UserRepository;

import jakarta.servlet.http.HttpSession;



@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    
    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser"); 
    }

    @GetMapping("/account")
    public String viewAccount(HttpSession session, Model model) {
        User user = getLoggedInUser(session); 
        if (user == null) {
            return "redirect:/login"; 
        }
        model.addAttribute("user", user);
        return "account-edit"; 
    }

    @PostMapping("/account")
    public String updateAccount(@ModelAttribute User user, HttpSession session) {
        User loggedInUser = getLoggedInUser(session); 
        if (loggedInUser != null) {
            loggedInUser.setUsername(user.getUsername()); 
            loggedInUser.setEmail(user.getEmail());
            userRepository.save(loggedInUser); 
            session.setAttribute("loggedInUser", loggedInUser); 
        }
        return "redirect:/account"; 
    }

    @GetMapping("/account/{userId}/orders")
    public String viewOrderHistory(HttpSession session, Model model) {
        User user = getLoggedInUser(session); 
        if (user == null) {
            return "redirect:/login"; 
        }
        List<Order> orders = user.getOrders(); 
        if (orders.isEmpty()) {
            model.addAttribute("message", "You have no orders yet."); 
        }
        model.addAttribute("orders", orders);
        return "order-history"; 
    }
}
