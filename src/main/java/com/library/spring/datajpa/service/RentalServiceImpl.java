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

import java.util.Date;
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
    public Optional<RentalDto> findById(long id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isPresent()) {
            Rental foundRental = rental.get();
            return Optional.of(
                    new RentalDto(
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
            return new RentalDto(foundClient.get().getId(), foundBook.get().getId(), null);
        }

        Rental savedComicBookRental = saveComicBookRental(rentalDto, foundClient.get());
        return new RentalDto(foundClient.get().getId(), null, savedComicBookRental.getComicBook().getId());
    }


    private Rental saveComicBookRental(RentalDto rentalDto, Client foundClient) {
        Rental rental;
        if (rentalDto.getComicBookId() == null) {
            throw new IllegalArgumentException("book_id and comicbook_id cannot both be null!");
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
    public Optional<RentalDto> returnRental(long id, RentalDto rentalDto) {
        return Optional.empty();
    }

//    public Optional<RentalDto> returnRental(long id, Rental rental) {
//        Optional<Rental> rentalData = rentalRepository.findById(id);
//
//        if (rentalData.isPresent()) {
//            Rental _rental = rentalData.get();
//            //_rental.setClient(rental.getClient());
//            //_rental.setBook(rental.getBook());
//            //_rental.setComicBook(rental.getComicBook());
//            _rental.setReturnedDate(rental.getReturnedDate());
//            return Optional.of(rentalRepository.save(_rental));
//        } else {
//            return Optional.empty();
//        }
//    }

}
