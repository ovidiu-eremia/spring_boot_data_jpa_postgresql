package com.library.spring.datajpa.service;

import com.library.spring.datajpa.dto.RentalDto;
import com.library.spring.datajpa.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    List<RentalDto> getAllRentals(Long clientId, Long bookId, Long comicBookId);

    Optional<RentalDto> findById(Long id);

    RentalDto rent(RentalDto rentalDto);

    Optional<RentalDto> returnRental(Long id, RentalDto rentalDto);
}
