package com.ca4bookshop.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book-list";
    }

    @GetMapping("/books/add")
    public String showAddBookPage() {
        return "add-book";
    }

    @PostMapping("/books")
    public String addBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/edit")
    public String showEditBookPage(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/books/{id}/edit")
    public String editBook(@PathVariable Long id, @ModelAttribute Book book) {
        book.setId(id);
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String query, Model model) {
        List<Book> books = bookRepository.findByTitleContainingOrAuthorContainingOrCategoryContaining(query, query, query);
        model.addAttribute("books", books);
        return "customer-dashboard"; 
    }

    @GetMapping("/books/sort")
    public String sortBooks(@RequestParam String sort, Model model) {
        List<Book> books;
        switch (sort) {
            case "title":
                books = bookRepository.findAll(Sort.by(Sort.Order.asc("title")));
                break;
            case "author":
                books = bookRepository.findAll(Sort.by(Sort.Order.asc("author")));
                break;
            case "price":
                books = bookRepository.findAll(Sort.by(Sort.Order.asc("price")));
                break;
            default:
                books = bookRepository.findAll();
                break;
        }
        model.addAttribute("books", books);
        return "customer-dashboard";
    }
}
