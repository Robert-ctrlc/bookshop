package com.ca4bookshop.bookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ca4bookshop.bookshop.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategoryContaining(String category);
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorContaining(String author);
    List<Book> findByPublisherContaining(String publisher);
}
