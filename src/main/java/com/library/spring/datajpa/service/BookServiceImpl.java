package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.Book;
import com.library.spring.datajpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks(String title, String author) {
        List<Book> books = new ArrayList<Book>();

        if (title != null) {
            books.addAll(bookRepository.findByTitleContainingOrderByTitle(title));
        } else {
            if (author != null) {
                books.addAll(bookRepository.findByAuthorContainingOrderByAuthor(author));
            } else
                books.addAll(bookRepository.findAll(Sort.by("title", "author")));
        }

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> updateBook(Long id, Book book) {
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
