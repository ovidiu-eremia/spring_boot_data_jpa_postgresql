package com.library.spring.datajpa.controller;

import com.library.spring.datajpa.dto.RentalDto;
import com.library.spring.datajpa.model.Rental;
import com.library.spring.datajpa.repository.RentalRepository;
import com.library.spring.datajpa.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalService rentalService;

    @PostMapping("/rentals")
    public ResponseEntity<RentalDto> createRental(@RequestBody RentalDto rentalDto) {
        try {
            RentalDto _rental = rentalService.rent(rentalDto);
            return new ResponseEntity<>(_rental, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<RentalDto> returnRental(@PathVariable("id") long id, @RequestBody RentalDto rentalDto) {
        Optional<RentalDto> result = rentalService.returnRental(id, rentalDto);

        return result.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

