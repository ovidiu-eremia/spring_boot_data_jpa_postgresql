package com.library.spring.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.spring.datajpa.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByTitleContaining(String title);
	List<Book> findByAuthorContaining(String author);
}