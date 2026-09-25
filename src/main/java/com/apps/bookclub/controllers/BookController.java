package com.apps.bookclub.controllers;

import com.apps.bookclub.dtos.BookResponse;
import com.apps.bookclub.dtos.CreateBookRequest;
import com.apps.bookclub.entities.Book;
import com.apps.bookclub.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponse> getBooks() {
        return bookService.getAllBooks().stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getMeetingDate(),
                        book.getAverageRating()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getMeetingDate(),
                book.getAverageRating()
        );
    }

//    @PostMapping
//    public Book createBook(@RequestBody Book book) {
//        return bookService.createBook(book);
//    }

    @PostMapping
    public Book createBook(@RequestBody CreateBookRequest request) {
        return bookService.createBook(request);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}