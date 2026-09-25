package com.apps.bookclub.repositories;

import com.apps.bookclub.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository
        extends JpaRepository<Book, Long> {
}