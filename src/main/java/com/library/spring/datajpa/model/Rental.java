package com.library.spring.datajpa.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @SequenceGenerator(name = "rentals_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rentals_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_book_id")
    private ComicBook comicBook;

    @Column(name = "rented_date")
    private Date rentedDate;

    @Column(name = "returned_date")
    private Date returnedDate;

    public Rental() {

    }

    public Rental(Client client, Book book, ComicBook comicBook
            , Date rentedDate) {
        this.client = client;
        this.book = book;
        this.comicBook = comicBook;
        this.rentedDate = rentedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public ComicBook getComicBook() {
        return comicBook;
    }

    public void setComicBook(ComicBook comicBook) {
        this.comicBook = comicBook;
    }

    public Date getRentedDate() {
        return rentedDate;
    }

    public void setRentedDate(Date rentedDate) {
        this.rentedDate = rentedDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(id, rental.id) &&
                client.equals(rental.client) &&
                Objects.equals(book, rental.book) &&
                Objects.equals(comicBook, rental.comicBook) &&
                Objects.equals(rentedDate, rental.rentedDate) &&
                Objects.equals(returnedDate, rental.returnedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, book, comicBook, rentedDate, returnedDate);
    }
//    @Override
//    public String toString() {
//        return "Rental [id=" + id + ", client=" + client.toString() + ", book=" + book.toString()
//                + ", comicBook=" + comicBook != null?comicBook.toString():null + ", rentedDate=" + rentedDate
//                + ", returnedDate=" + returnedDate + "]";
//    }
}

