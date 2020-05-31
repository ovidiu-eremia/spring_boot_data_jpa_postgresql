package com.library.spring.datajpa.controller;

import com.library.spring.datajpa.model.Book;
import com.library.spring.datajpa.model.ComicBook;
import com.library.spring.datajpa.repository.ComicBookRepository;
import com.library.spring.datajpa.service.ComicBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ComicBookController {

    @Autowired
    private ComicBookRepository comicBookRepository;

    @Autowired
    private ComicBookService comicBookService;

    @GetMapping("/comic_books")
    public ResponseEntity<List<ComicBook>> getAllComicBooks(@RequestParam(required = false) String series
            , @RequestParam(required = false) Integer number) {
        try {
            List<ComicBook> comicBooks = comicBookService.getAllComicBooks(series, number);

            if (comicBooks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(comicBooks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/comic_books/{id}")
    public ResponseEntity<ComicBook> getComicBookById(@PathVariable("id") long id) {
        Optional<ComicBook> comicBookData = comicBookService.findById(id);

        return comicBookData.map(cb -> new ResponseEntity<>(cb, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/comic_books")
    public ResponseEntity<ComicBook> createComicBook(@RequestBody ComicBook comicBook) {
        try {
            ComicBook _comicBook = comicBookService
                    .save(new ComicBook(comicBook.getSeries(), comicBook.getNumber()));
            return new ResponseEntity<>(_comicBook, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/comic_books/{id}")
    public ResponseEntity<ComicBook> updateComicBook(@PathVariable("id") long id, @RequestBody ComicBook comicBook) {
        Optional<ComicBook> result = comicBookService.updateComicBook(id, comicBook);

        return result.map(cb -> new ResponseEntity<>(cb, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/comic_books/{id}")
    public ResponseEntity<HttpStatus> deleteComicBook(@PathVariable("id") long id) {
        try {
            comicBookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/comic_books")
    public ResponseEntity<HttpStatus> deleteAllComicBooks() {
        try {
            comicBookRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

    }

}

