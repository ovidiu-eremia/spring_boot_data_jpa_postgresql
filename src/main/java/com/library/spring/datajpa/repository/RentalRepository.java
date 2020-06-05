package com.library.spring.datajpa.repository;

import com.library.spring.datajpa.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByClientId(Long clientId);
    List<Rental> findByBookId(Long bookId);
    List<Rental> findByComicBookId(Long comicBookId);

    @Query(value = "select case when count(r)> 0 then false else true end from Rental r where r.book.id = :bookId")
    boolean checkBookAvailability(@Param("bookId") Long bookId);
}