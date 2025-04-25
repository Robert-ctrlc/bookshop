package com.ca4bookshop.bookshop.repository;

import com.ca4bookshop.bookshop.model.Cart;
import com.ca4bookshop.bookshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
   
    List<Cart> findByUser(User user);
}
