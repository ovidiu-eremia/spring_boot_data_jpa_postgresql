package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.Book;
import com.library.spring.datajpa.model.Client;
import com.library.spring.datajpa.model.ComicBook;
import com.library.spring.datajpa.model.Rental;

public interface RentalService {
    Rental save(Client client, Book book);

    Rental save(Client client, ComicBook comicBook);
}
