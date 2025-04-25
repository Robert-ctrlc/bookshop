package com.ca4bookshop.bookshop.repository;

import com.ca4bookshop.bookshop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
   
}
