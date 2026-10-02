package com.apps.bookclub.repositories;

import com.apps.bookclub.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository
        extends JpaRepository<Book, Long> {
    List<Book> findAllByOrderByMeetingDateDesc();
}