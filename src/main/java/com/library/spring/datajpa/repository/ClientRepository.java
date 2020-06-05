package com.library.spring.datajpa.repository;

import com.library.spring.datajpa.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
	List<Client> findByNameContainingOrderByName(String name);
}