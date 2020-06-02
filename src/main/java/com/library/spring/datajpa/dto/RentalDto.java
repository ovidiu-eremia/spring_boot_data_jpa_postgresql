package com.library.spring.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class RentalDto {
    public RentalDto(Long clientId, Long bookId, Long comicBookId) {
        this.clientId = clientId;
        this.bookId = bookId;
        this.comicBookId = comicBookId;
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

    private Long clientId;
    private Long bookId;
    private Long comicBookId;
}
