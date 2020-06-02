package com.library.spring.datajpa.service;

import com.library.spring.datajpa.dto.RentalDto;

import java.util.Optional;

public interface RentalService {
    Optional<RentalDto> findById(long id);

    RentalDto rent(RentalDto rentalDto);

    Optional<RentalDto> returnRental(long id, RentalDto rentalDto);
}
