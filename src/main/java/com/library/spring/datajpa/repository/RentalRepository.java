package com.library.spring.datajpa.repository;

import com.library.spring.datajpa.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByClientId(Long clientId);
    List<Rental> findByBookId(Long bookId);
    List<Rental> findByComicBookId(Long comicBookId);
}