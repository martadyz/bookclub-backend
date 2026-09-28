package com.apps.bookclub.services;

import com.apps.bookclub.dtos.CreateBookRequest;
import com.apps.bookclub.entities.Book;
import com.apps.bookclub.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Book not found"));
    }

    public Book createBook(CreateBookRequest request) {

        Book book = new Book(
                request.title(),
                request.meetingDate()
        );

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, CreateBookRequest request) {
        Book book = getBook(id);

        book.setTitle(request.title());
        book.setMeetingDate(request.meetingDate());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
