package com.library.spring.datajpa.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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

    public Rental(Client client, Book book) {
        this.client = client;
        this.book = book;
    }

    public Rental(Client client, ComicBook comicBook) {
        this.client = client;
        this.comicBook = comicBook;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public String toString() {
        return "Rental [id=" + id + ", client=" + client.toString() + ", book=" + book.toString()
                + ", comicBook=" + comicBook.toString() + ", rentedDate=" + rentedDate
                + ", returnedDate=" + returnedDate + "]";
    }
}

