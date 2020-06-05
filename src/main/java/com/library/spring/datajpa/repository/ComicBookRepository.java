package com.library.spring.datajpa.repository;

import com.library.spring.datajpa.model.ComicBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComicBookRepository extends JpaRepository<ComicBook, Long> {
	List<ComicBook> findBySeriesContainingOrderBySeries(String series);
	List<ComicBook> findByNumberOrderByNumber(Integer number);
}