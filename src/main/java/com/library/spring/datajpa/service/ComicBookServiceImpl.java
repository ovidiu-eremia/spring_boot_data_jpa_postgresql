package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.ComicBook;
import com.library.spring.datajpa.repository.ComicBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComicBookServiceImpl implements ComicBookService {

    @Autowired
    private ComicBookRepository comicBookRepository;

    @Override
    public List<ComicBook> getAllComicBooks(String series, Integer number) {
        List<ComicBook> comicBooks = new ArrayList<ComicBook>();

        if (series != null) {
            comicBookRepository.findBySeriesContaining(series).forEach(comicBooks::add);
        } else {
            if (number != null) {
                comicBookRepository.findByNumber(number).forEach(comicBooks::add);
            } else {
                comicBookRepository.findAll().forEach(comicBooks::add);
            }
        }

        return comicBooks;
    }

    @Override
    public Optional<ComicBook> findById(Long id) {
        return comicBookRepository.findById(id);
    }

    @Override
    public ComicBook save(ComicBook comicBook) {
        return comicBookRepository.save(comicBook);
    }

    @Override
    public void deleteById(Long id) {
        comicBookRepository.deleteById(id);
    }

    @Override
    public Optional<ComicBook> updateComicBook(Long id, ComicBook comicBook) {
        Optional<ComicBook> comicBookData = comicBookRepository.findById(id);

        if (comicBookData.isPresent()) {
            ComicBook _comicBook = comicBookData.get();
            _comicBook.setSeries(comicBook.getSeries());
            _comicBook.setNumber(comicBook.getNumber());
            return Optional.of(comicBookRepository.save(_comicBook));
        } else {
            return Optional.empty();
        }
    }

}
