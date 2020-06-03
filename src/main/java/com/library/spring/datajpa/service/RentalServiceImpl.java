package com.library.spring.datajpa.service;

import com.library.spring.datajpa.dto.RentalDto;
import com.library.spring.datajpa.model.Book;
import com.library.spring.datajpa.model.Client;
import com.library.spring.datajpa.model.ComicBook;
import com.library.spring.datajpa.model.Rental;
import com.library.spring.datajpa.repository.BookRepository;
import com.library.spring.datajpa.repository.ClientRepository;
import com.library.spring.datajpa.repository.ComicBookRepository;
import com.library.spring.datajpa.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ComicBookRepository comicBookRepository;

    @Override
    public List<RentalDto> getAllRentals(Long clientId, Long bookId, Long comicBookId) {
        List<Rental> rentals = new ArrayList<Rental>();

        if (clientId != null) {
            rentals.addAll(rentalRepository.findByClientId(clientId));
        } else if (bookId != null) {
            rentals.addAll(rentalRepository.findByBookId(bookId));
        } else if (comicBookId != null) {
            rentals.addAll(rentalRepository.findByComicBookId(comicBookId));
        } else rentals.addAll(rentalRepository.findAll());

        return getRentalDtos(rentals);
    }

    private List<RentalDto> getRentalDtos(List<Rental> rentals) {
        List<RentalDto> rentalDtos = new ArrayList<>();

        for (Rental rental : rentals) {
            rentalDtos.add(new RentalDto(
                    rental.getId(),
                    rental.getClient().getId(),
                    rental.getBook().getId(),
                    rental.getComicBook().getId()
            ));
        }
        return rentalDtos;
    }

    @Override
    public Optional<RentalDto> findById(Long id) {
        Optional<Rental> rentalOpt = rentalRepository.findById(id);

        if (rentalOpt.isPresent()) {
            Rental foundRental = rentalOpt.get();
            return Optional.of(
                    new RentalDto(
                            foundRental.getId(),
                            foundRental.getClient().getId(),
                            foundRental.getBook().getId(),
                            foundRental.getComicBook().getId()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public RentalDto rent(RentalDto rentalDto) {
        Optional<Client> foundClient = clientRepository.findById(rentalDto.getClientId());
        if (!foundClient.isPresent()) {
            throw new IllegalArgumentException("Client not found!");
        }

        Rental rental;
        if (rentalDto.getBookId() != null) {
            Optional<Book> foundBook = bookRepository.findById(rentalDto.getBookId());
            if (foundBook.isPresent()) {
                rental = new Rental(foundClient.get(), foundBook.get(), null, new Date());
            } else {
                throw new IllegalArgumentException("Book not found!");
            }
            rentalRepository.save(rental);
            return new RentalDto(rental.getId(), foundClient.get().getId(), foundBook.get().getId(), null);
        }

        Rental savedComicBookRental = saveComicBookRental(rentalDto, foundClient.get());
        return new RentalDto(savedComicBookRental.getId(), foundClient.get().getId(), null, savedComicBookRental.getComicBook().getId());
    }


    private Rental saveComicBookRental(RentalDto rentalDto, Client foundClient) {
        Rental rental;
        if (rentalDto.getComicBookId() == null) {
            throw new IllegalArgumentException("bookId and comicBookId cannot both be null!");
        }

        Optional<ComicBook> foundComicBook = comicBookRepository.findById(rentalDto.getComicBookId());

        if (foundComicBook.isPresent()) {
            rental = new Rental(foundClient, null, foundComicBook.get(), new Date());
        } else {
            throw new IllegalArgumentException("Book not found!");
        }
        return rentalRepository.save(rental);
    }

    @Override
    public Optional<RentalDto> returnRental(Long id, RentalDto rentalDto) {
        Optional<Rental> rentalOpt = rentalRepository.findById(id);

        if (rentalOpt.isPresent()) {
            Rental foundRental = rentalOpt.get();
            foundRental.setReturnedDate(new Date());
            Rental savedRental = rentalRepository.save(foundRental);
            return Optional.of(
                    new RentalDto(savedRental.getId(),
                            savedRental.getClient().getId(),
                            Optional.ofNullable(savedRental.getBook())
                                    .map(Book::getId)
                                    .orElse(null),
                            Optional.ofNullable(savedRental.getComicBook())
                                    .map(ComicBook::getId)
                                    .orElse(null),
                            savedRental.getReturnedDate()));
        } else {
            return Optional.empty();
        }
    }
}
