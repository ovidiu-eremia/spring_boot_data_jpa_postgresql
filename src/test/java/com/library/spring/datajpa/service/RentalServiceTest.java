package com.library.spring.datajpa.service;

import com.library.spring.datajpa.dto.RentalDto;
import com.library.spring.datajpa.model.Book;
import com.library.spring.datajpa.model.Client;

import com.library.spring.datajpa.model.ComicBook;
import com.library.spring.datajpa.model.Rental;
import com.library.spring.datajpa.repository.BookRepository;
import com.library.spring.datajpa.repository.ClientRepository;
import com.library.spring.datajpa.repository.RentalRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RentalServiceTest {
    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private RentalServiceImpl underTest;

    @Test
    public void findById() {
        //given
        long id = 1L;
        Rental rental = new Rental(new Client(), new Book(), new ComicBook(), new Date());
        rental.setId(id);
        given(rentalRepository.findById(id)).willReturn(Optional.of(rental));
        RentalDto rentalDto = new RentalDto(rental.getId(), rental.getClient().getId(),
                rental.getBook().getId(), rental.getComicBook().getId());

        //when
        Optional<RentalDto> result = underTest.findById(id);

        //then
        assertThat(result.get()).isEqualTo(rentalDto);
        verify(rentalRepository).findById(id);
    }

    @Test
    public void rent() {
        //given
        Long clientId = 2L;
        Long bookId = 3L;
        Rental rental = new Rental(new Client(), new Book(), new ComicBook(), new Date());
        rental.getClient().setId(clientId);
        rental.getBook().setId(bookId);
        Client client = new Client("Test");
        client.setId(clientId);
        Book book = new Book("Book name", "Book author");
        book.setId(bookId);
        given(rentalRepository.save(any())).willReturn(rental);
        given(clientRepository.findById(clientId)).willReturn(Optional.of(client));
        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));
        given(rentalRepository.checkBookAvailability(bookId)).willReturn(true);
        RentalDto rentalDto = new RentalDto(rental.getId(), rental.getClient().getId(),
                rental.getBook().getId(), rental.getComicBook().getId());

        //when
        RentalDto result = underTest.rent(rentalDto);

        //then
        assertThat(result).isEqualTo(rentalDto);
        verify(rentalRepository).save(any());
    }

    @Test
    public void ReturnRentalWhenIdFound() {
        //given
        long id = 3L;
        Rental rental = new Rental(new Client(), new Book(), new ComicBook(), new Date());
        rental.setId(id);
        Rental returnedRental = new Rental(new Client(), new Book(), new ComicBook(), new Date());
        given(rentalRepository.findById(id)).willReturn(Optional.of(rental));
        given(rentalRepository.save(any())).willReturn(returnedRental);
        RentalDto rentalDto = new RentalDto(rental.getId(), rental.getClient().getId(),
                rental.getBook().getId(), rental.getComicBook().getId());
        RentalDto returnedRentalDto = new RentalDto(returnedRental.getId(), returnedRental.getClient().getId(),
                returnedRental.getBook().getId(), returnedRental.getComicBook().getId());

        //when
        Optional<RentalDto> result = underTest.returnRental(id, returnedRentalDto);

        //then
        assertThat(result.get()).isEqualTo(returnedRentalDto);
        verify(rentalRepository).findById(id);
        verify(rentalRepository).save(eq(rental));
    }

    @Test
    public void ReturnRentalWhenIdNotFound() {
        //given
        long id = 3L;
        Rental returnedRental = new Rental(new Client(), new Book(), new ComicBook(), new Date());
        given(rentalRepository.findById(id)).willReturn(Optional.empty());
        RentalDto returnedRentalDto = new RentalDto(returnedRental.getId(), returnedRental.getClient().getId(),
                returnedRental.getBook().getId(), returnedRental.getComicBook().getId());

        //when
        Optional<RentalDto> result = underTest.returnRental(id, returnedRentalDto);

        //then
        assertThat(result).isEqualTo(Optional.empty());
        verify(rentalRepository).findById(id);
        verify(rentalRepository,times(0)).save(any());
    }
}