package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.Book;
import com.library.spring.datajpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks(String title) {
        List<Book> books = new ArrayList<Book>();

        if (title != null) {
            bookRepository.findByTitleContaining(title).forEach(books::add);
        } else {
            bookRepository.findAll().forEach(books::add);
        }

        return books;
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> updateBook(long id, Book book) {
        Optional<Book> bookData = bookRepository.findById(id);

        if (bookData.isPresent()) {
            Book _book = bookData.get();
            _book.setTitle(book.getTitle());
            _book.setAuthor(book.getAuthor());
            return Optional.of(bookRepository.save(_book));
        } else {
            return Optional.empty();
        }
    }

}
