package com.library.spring.datajpa.model;

import javax.persistence.*;

@Entity(name = "Client")
@Table(name = "clients")
public class Client {

	@Id
	@SequenceGenerator(name="clients_sequence", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_sequence")
	private long id;

	@Column(name = "name")
	private String name;

	public Client() {

	}

	public Client(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + "]";
	}

}

