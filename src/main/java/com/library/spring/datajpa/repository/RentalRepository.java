package com.library.spring.datajpa.repository;

import com.library.spring.datajpa.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}