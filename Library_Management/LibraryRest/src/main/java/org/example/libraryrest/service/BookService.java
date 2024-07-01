package org.example.libraryrest.service;

import org.example.libraryrest.entity.Book;
import org.example.libraryrest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Book getBookById(long id) {
        return bookRepository.getReferenceById(id);
    }
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }


    public List<Book> getList(List<Long> bids) {
        List<Book> books= new ArrayList<>();
        for(Long id : bids) {
            books.add(getBookById(id));
        }
        return books;
    }
}
