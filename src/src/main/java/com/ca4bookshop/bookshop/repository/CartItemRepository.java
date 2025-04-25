package com.ca4bookshop.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ca4bookshop.bookshop.model.CartItem;
import com.ca4bookshop.bookshop.model.Cart;
import com.ca4bookshop.bookshop.model.Book;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByCart(Cart cart);
    
    
    CartItem findByCartAndBook(Cart cart, Book book);
}
