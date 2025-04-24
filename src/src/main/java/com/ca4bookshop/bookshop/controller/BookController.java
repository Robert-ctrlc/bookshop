package com.ca4bookshop.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ca4bookshop.bookshop.model.Book;
import com.ca4bookshop.bookshop.repository.BookRepository;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index"; 
    }

    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "book-details";
    }
}
