package com.ca4bookshop.bookshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ca4bookshop.bookshop.model.Cart;
import com.ca4bookshop.bookshop.model.CartItem;
import com.ca4bookshop.bookshop.model.User;

import jakarta.servlet.http.HttpSession;

import com.ca4bookshop.bookshop.model.Book;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    
    public void addItemToCart(Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        
        
        Cart cart = getLoggedInUserCart(null);
        
        
        CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book);
        if (cartItem == null) {
            
            cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItemRepository.save(cartItem);
        } else {
            
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        }
    }

    
    public void removeItemFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItemRepository.delete(cartItem);
    }

   
    public List<CartItem> getCartItems() {
        Cart cart = getLoggedInUserCart(null);
        return cartItemRepository.findByCart(cart);
    }

   
    public void checkout() {
        
    }

   
    public Cart getLoggedInUserCart(HttpSession session) {
        User loggedInUser = getLoggedInUser(session); 
        if (loggedInUser == null) {
            throw new RuntimeException("No user found in session");
        }
    
        List<Cart> carts = cartRepository.findByUser(loggedInUser);
        if (carts.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }
        return carts.get(0);  
    }
    
    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    public List<Cart> getCartItemsForUser(User user) {
        return cartRepository.findByUser(user);  
    }
}

