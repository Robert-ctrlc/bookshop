package com.ca4bookshop.bookshop.controller;

import com.ca4bookshop.bookshop.model.Book;
import com.ca4bookshop.bookshop.model.Order;
import com.ca4bookshop.bookshop.repository.OrderRepository;
import com.ca4bookshop.bookshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/place-order")
    public String placeOrder(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            Order order = new Order();
            order.setUser(user);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus("Pending");

            
            double totalPrice = calculateTotalPrice(order);
            order.setTotalPrice(totalPrice);

            
            orderRepository.save(order);

            return "redirect:/order-history";  
        }
        return "redirect:/login";  
    }

    private double calculateTotalPrice(Order order) {
        
        double total = 0;
        for (Book book : order.getBooks()) {
            total += book.getPrice();
        }
        return total;
    }

    @GetMapping("/order-history")
    public String viewOrderHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            List<Order> orders = orderRepository.findByUser(user);
            model.addAttribute("orders", orders);
            return "order-history";  
        }
        return "redirect:/login";  
    }
}
