package com.library.spring.datajpa.model;

import javax.persistence.*;

@Entity(name = "Book")
@Table(name = "books")
public class Book {

    @Id
    @SequenceGenerator(name="books_sequence", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_sequence")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    public Book() {

    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", auth=" + author + "]";
    }

}

