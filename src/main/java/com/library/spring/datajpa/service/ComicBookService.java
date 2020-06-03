package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.ComicBook;

import java.util.List;
import java.util.Optional;

public interface ComicBookService {
    List<ComicBook> getAllComicBooks(String series, Integer number);

    Optional<ComicBook> findById(Long id);

    ComicBook save(ComicBook comicBook);

    Optional<ComicBook> updateComicBook(Long id, ComicBook comicBook);

    void deleteById(Long id);
}
