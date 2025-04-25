package com.ca4bookshop.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ca4bookshop.bookshop.model.Review;
import com.ca4bookshop.bookshop.repository.ReviewRepository;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("/review/{bookId}")
    public String submitReview(@PathVariable Long bookId, @RequestParam int rating, @RequestParam String comment) {
        Review review = new Review();
        review.setBookId(bookId);
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
        return "redirect:/books/{bookId}";  
    }
}
