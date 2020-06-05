package com.library.spring.datajpa.dto;

//import lombok.AllArgsConstructor;
//import lombok.Data;

import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Objects;

//@EqualsAndHashCode
public class RentalDto {
    public RentalDto() {
    }

    public RentalDto(Long id, Long clientId, Long bookId, Long comicBookId) {
        this.id = id;
        this.clientId = clientId;
        this.bookId = bookId;
        this.comicBookId = comicBookId;
    }

    public RentalDto(Long id, Long clientId, Long bookId, Long comicBookId, Date returnedDate) {
        this.id = id;
        this.clientId = clientId;
        this.bookId = bookId;
        this.comicBookId = comicBookId;
        this.returnedDate = returnedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getComicBookId() {
        return comicBookId;
    }

    public void setComicBookId(Long comicBookId) {
        this.comicBookId = comicBookId;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    private Long id;
    private Long clientId;
    private Long bookId;
    private Long comicBookId;
    private Date returnedDate;

    @Override
    public String toString() {
        return "RentalDto{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", bookId=" + bookId +
                ", comicBookId=" + comicBookId +
                ", returnedDate=" + returnedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDto rentalDto = (RentalDto) o;
        return Objects.equals(id, rentalDto.id) &&
                Objects.equals(clientId, rentalDto.clientId) &&
                Objects.equals(bookId, rentalDto.bookId) &&
                Objects.equals(comicBookId, rentalDto.comicBookId) &&
                Objects.equals(returnedDate, rentalDto.returnedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, bookId, comicBookId, returnedDate);
    }
}
