package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.Book;
import com.library.spring.datajpa.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void getAllBooksWhenTitleNotNull() {
        //given
        String title = "Some book";
        String author = "Some author";
        Book book = new Book(title, author);
        List<Book> books = Collections.singletonList(book);

        given(bookRepository.findByTitleContaining(title)).willReturn(books);

        //when
        List<Book> result = underTest.getAllBooks(title, null);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(book);

        verify(bookRepository).findByTitleContaining(title);
        verify(bookRepository, times(0)).findAll();
    }

    @Test
    public void getAllBooksWhenAuthorNotNull() {
        //given
        String title = "Some book";
        String author = "Some author";
        Book book = new Book(title, author);
        List<Book> books = Collections.singletonList(book);

        given(bookRepository.findByAuthorContaining(author)).willReturn(books);

        //when
        List<Book> result = underTest.getAllBooks(null, author);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(book);

        verify(bookRepository).findByAuthorContaining(author);
        verify(bookRepository, times(0)).findAll();
    }

    @Test
    public void getAllBooksWhenTitleNull() {
        //given
        Book book = new Book("Some book", "Some author");
        List<Book> books = Collections.singletonList(book);

        given(bookRepository.findAll()).willReturn(books);

        //when
        List<Book> result = underTest.getAllBooks(null, null);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(book);

        verify(bookRepository).findAll();
        verify(bookRepository, times(0)).findByTitleContaining(any());
    }

    @Test
    public void getAllBooksWhenAuthorNull() {
        //given
        Book book = new Book("Some book", "Some author");
        List<Book> books = Collections.singletonList(book);

        given(bookRepository.findAll()).willReturn(books);

        //when
        List<Book> result = underTest.getAllBooks(null, null);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(book);

        verify(bookRepository).findAll();
        verify(bookRepository, times(0)).findByAuthorContaining(any());
    }

    @Test
    public void findById() {
        long id = 3L;

        Book book = new Book("Some book", "Some author");

        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        Optional<Book> result = underTest.findById(id);

        assertThat(result.get()).isEqualTo(book);

        verify(bookRepository).findById(id);
    }

    @Test
    public void save() {
        //given
        long id = 3L;
        Book book = new Book("Some book", "Some author");
        given(bookRepository.save(book)).willReturn(book);

        //when
        Book result = underTest.save(book);

        //then
        assertThat(result).isEqualTo(book);
        verify(bookRepository).save(book);
    }

    @Test
    public void deleteById() {
        //given
        long id = 3L;
        Book book = new Book("Some book", "Some author");
        doNothing().when(bookRepository).deleteById(id);

        //when
        underTest.deleteById(id);

        //then
        verify(bookRepository).deleteById(id);
    }

    @Test
    public void updateBookWhenBookFound() {
        //given
        long id = 3L;
        Book book = new Book("Some book", "Some author");
        book.setId(id);
        Book updatedBook = new Book("Another book", "Another author");
        given(bookRepository.findById(id)).willReturn(Optional.of(book));
        given(bookRepository.save(any())).willReturn(updatedBook);

        //when
        Optional<Book> result = underTest.updateBook(id, updatedBook);

        //then
        assertThat(result.get()).isEqualTo(updatedBook);
        verify(bookRepository).findById(id);
        verify(bookRepository).save(eq(book));
    }

    @Test
    public void updateBookWhenBookNotFound() {
        //given
        long id = 3L;
        Book book = new Book("Some book", "Some author");
        book.setId(id);
        Book updatedBook = new Book("Another book", "Another author");
        given(bookRepository.findById(id)).willReturn(Optional.empty());

        //when
        Optional<Book> result = underTest.updateBook(id, updatedBook);

        //then
        assertThat(result).isEqualTo(Optional.empty());
        verify(bookRepository).findById(id);
        verify(bookRepository,times(0)).save(any());
    }
}