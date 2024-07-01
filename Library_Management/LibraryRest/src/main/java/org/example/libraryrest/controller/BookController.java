package org.example.libraryrest.controller;

import org.example.libraryrest.entity.Book;
import org.example.libraryrest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/books/")
public class BookController {
    @Autowired
    private BookService bookService;
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("number/")
    public Integer getNumberOfBooks() {
        int n= bookService.getAllBooks().size();
        return n;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public Book addBook(@RequestBody Book book) {
        System.out.println(book.getAuthor());
        return bookService.saveBook(book);
    }
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("{id}")
    public Book updateBook( @PathVariable int id, @RequestBody Book book) {
        Book oldBook = bookService.getBookById(id);
        oldBook.setTitle(book.getTitle());
        oldBook.setAuthor(book.getAuthor());
        bookService.saveBook(oldBook);
        return oldBook;
    }


}
