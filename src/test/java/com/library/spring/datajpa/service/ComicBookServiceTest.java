package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.ComicBook;
import com.library.spring.datajpa.repository.ComicBookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComicBookServiceTest {
    @Mock
    private ComicBookRepository comicBookRepository;

    @InjectMocks
    private ComicBookServiceImpl underTest;

    @Test
    public void getAllComicBooksWhenSeriesNotNull() {
        //given
        String series = "Some series";
        Integer number = 123;
        ComicBook comicBook = new ComicBook(series, number);
        List<ComicBook> comicBooks = Collections.singletonList(comicBook);

        given(comicBookRepository.findBySeriesContainingOrderBySeries(series)).willReturn(comicBooks);

        //when
        List<ComicBook> result = underTest.getAllComicBooks(series, null);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(comicBook);

        verify(comicBookRepository).findBySeriesContainingOrderBySeries(series);
        verify(comicBookRepository, times(0)).findAll();
    }

    @Test
    public void getAllComicBooksWhenNumberNotNull() {
        //given
        String series = "Some series";
        Integer number = 123;
        ComicBook comicBook = new ComicBook(series, number);
        List<ComicBook> comicBooks = Collections.singletonList(comicBook);

        given(comicBookRepository.findByNumberOrderByNumber(number)).willReturn(comicBooks);

        //when
        List<ComicBook> result = underTest.getAllComicBooks(null, number);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(comicBook);

        verify(comicBookRepository).findByNumberOrderByNumber(number);
        verify(comicBookRepository, times(0)).findAll();
    }

    @Test
    public void getAllComicBooksWhenSeriesNull() {
        //given
        ComicBook comicBook = new ComicBook("Some series", 123);
        List<ComicBook> comicBooks = Collections.singletonList(comicBook);

        given(comicBookRepository.findAll(Sort.by(Sort.Order.asc("series"), Sort.Order.desc("number")))).willReturn(comicBooks);

        //when
        List<ComicBook> result = underTest.getAllComicBooks(null, null);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(comicBook);

        verify(comicBookRepository).findAll(Sort.by(Sort.Order.asc("series"), Sort.Order.desc("number")));
        verify(comicBookRepository, times(0)).findBySeriesContainingOrderBySeries(any());
    }

    @Test
    public void getAllComicBooksWhenNumberNull() {
        //given
        ComicBook comicBook = new ComicBook("Some series", 123);
        List<ComicBook> comicBooks = Collections.singletonList(comicBook);

        given(comicBookRepository.findAll(Sort.by(Sort.Order.asc("series"), Sort.Order.desc("number")))).willReturn(comicBooks);

        //when
        List<ComicBook> result = underTest.getAllComicBooks(null, null);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(comicBook);

        verify(comicBookRepository).findAll(Sort.by(Sort.Order.asc("series"), Sort.Order.desc("number")));
        verify(comicBookRepository, times(0)).findByNumberOrderByNumber(any());
    }

    @Test
    public void findById() {
        long id = 3L;

        ComicBook comicBook = new ComicBook("Some book", 123);

        given(comicBookRepository.findById(id)).willReturn(Optional.of(comicBook));

        Optional<ComicBook> result = underTest.findById(id);

        assertThat(result.get()).isEqualTo(comicBook);

        verify(comicBookRepository).findById(id);
    }

    @Test
    public void save() {
        //given
        long id = 3L;
        ComicBook comicBook = new ComicBook("Some comic book", 123);
        given(comicBookRepository.save(comicBook)).willReturn(comicBook);

        //when
        ComicBook result = underTest.save(comicBook);

        //then
        assertThat(result).isEqualTo(comicBook);
        verify(comicBookRepository).save(comicBook);
    }

    @Test
    public void deleteById() {
        //given
        long id = 3L;
        ComicBook comicBook = new ComicBook("Some comic book", 123);
        doNothing().when(comicBookRepository).deleteById(id);

        //when
        underTest.deleteById(id);

        //then
        verify(comicBookRepository).deleteById(id);
    }

    @Test
    public void updateComicBookWhenIdFound() {
        //given
        long id = 3L;
        ComicBook comicBook = new ComicBook("Some comic book", 123);
        comicBook.setId(id);
        ComicBook updatedComicBook = new ComicBook("Another comic book", 456);
        given(comicBookRepository.findById(id)).willReturn(Optional.of(comicBook));
        given(comicBookRepository.save(any())).willReturn(updatedComicBook);

        //when
        Optional<ComicBook> result = underTest.updateComicBook(id, updatedComicBook);

        //then
        assertThat(result.get()).isEqualTo(updatedComicBook);
        verify(comicBookRepository).findById(id);
        verify(comicBookRepository).save(eq(comicBook));
    }

    @Test
    public void updateComicBookWhenIdNotFound() {
        //given
        long id = 3L;
        ComicBook comicBook = new ComicBook("Some book", 123);
        comicBook.setId(id);
        ComicBook updatedComicBook = new ComicBook("Another book", 456);
        given(comicBookRepository.findById(id)).willReturn(Optional.empty());

        //when
        Optional<ComicBook> result = underTest.updateComicBook(id, updatedComicBook);

        //then
        assertThat(result).isEqualTo(Optional.empty());
        verify(comicBookRepository).findById(id);
        verify(comicBookRepository,times(0)).save(any());
    }
}