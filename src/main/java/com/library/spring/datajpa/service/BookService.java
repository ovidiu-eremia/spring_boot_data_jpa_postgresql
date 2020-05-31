package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks(String title, String author);

    Optional<Book> findById(long id);

    Book save(Book book);

    Optional<Book> updateBook(long id, Book book);

    void deleteById(long id);
}
