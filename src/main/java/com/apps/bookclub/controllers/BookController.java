package com.apps.bookclub.controllers;

import com.apps.bookclub.dtos.BookResponse;
import com.apps.bookclub.dtos.CreateBookRequest;
import com.apps.bookclub.entities.Book;
import com.apps.bookclub.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponse> getBooks(@RequestParam(required = false) String sort) {
        List<BookResponse> books = bookService.getAllBooks().stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getMeetingDate(),
                        book.getAverageRating(),
                        book.getRatings()
                ))
                .toList();

        return sort == null ? books : books.stream().sorted(bookComparator(sort)).toList();
    }

    private Comparator<BookResponse> bookComparator(String sort) {
        String[] sortParts = sort.split(",", -1);
        if (sortParts.length > 2) {
            throw invalidSortParameter();
        }

        Comparator<BookResponse> comparator = switch (sortParts[0].trim()) {
            case "meetingDate" -> Comparator.comparing(BookResponse::meetingDate);
            case "averageRating" -> Comparator.comparingDouble(BookResponse::averageRating);
            default -> throw invalidSortParameter();
        };

        String direction = sortParts.length == 1
                ? "desc"
                : sortParts[1].trim().toLowerCase(Locale.ROOT);
        return switch (direction) {
            case "asc" -> comparator;
            case "desc" -> comparator.reversed();
            default -> throw invalidSortParameter();
        };
    }

    private ResponseStatusException invalidSortParameter() {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "sort must be meetingDate or averageRating, optionally followed by asc or desc"
        );
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getMeetingDate(),
                book.getAverageRating(),
                book.getRatings()
        );
    }


    @PostMapping
    public Book createBook(@RequestBody CreateBookRequest request) {
        return bookService.createBook(request);
    }

    @PutMapping("/{id}")
    public Book updateBook(
            @PathVariable Long id,
            @RequestBody CreateBookRequest request) {

        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}