package com.library.spring.datajpa.model;

import javax.persistence.*;

@Entity
@Table(name = "comic_books")
public class ComicBook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "series")
	private String series;

	@Column(name = "number")
	private Integer number;

	public ComicBook() {

	}

	public ComicBook(String series, Integer number) {
		this.series = series;
		this.number = number;
	}

	public long getId() {
		return id;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ComicBook [id=" + id + ", series=" + series + ", auth=" + number + "]";
	}

}
