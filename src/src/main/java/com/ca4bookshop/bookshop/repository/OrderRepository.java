package com.ca4bookshop.bookshop.repository;

import com.ca4bookshop.bookshop.model.Order;
import com.ca4bookshop.bookshop.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
