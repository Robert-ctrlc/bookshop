package com.ca4bookshop.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ca4bookshop.bookshop.model.User;
import com.ca4bookshop.bookshop.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        try {
            userRepository.save(user);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/register/admin")
    public String showAdminRegistrationPage() {
        return "admin-register";
    }

    @PostMapping("/register/admin")
    public String registerAdmin(@ModelAttribute User user, Model model) {
        try {
            user.setRole("ADMIN");
            userRepository.save(user);
            return "redirect:/login/admin";
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while registering admin");
            e.printStackTrace();
            return "admin-register";
        }
    }

    @GetMapping("/login/admin")
    public String showAdminLoginPage() {
        return "admin-login";
    }

    @PostMapping("/login/admin")
    public String adminLogin(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getRole().equals("ADMIN")) {
            return "redirect:/admin-dashboard";  
        }
        model.addAttribute("error", "Invalid admin credentials");
        return "admin-login";  
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            User user = userRepository.findByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return "redirect:/";
            }
            model.addAttribute("error", "Invalid credentials");
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error occurred while logging in");
            return "login";
        }
    }

    @GetMapping("/admin-dashboard")
    public String showAdminDashboard() {
        return "admin-dashboard";  
    }

    
    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list"; 
    }

    
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "user-edit";  
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        User existingUser = userRepository.findById(id).orElseThrow();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        userRepository.save(existingUser);
        return "redirect:/users"; 
    }

    
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users"; 
    }
}
