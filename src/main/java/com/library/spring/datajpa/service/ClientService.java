package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAllClients(String name);

    Optional<Client> findById(Long id);

    Client save(Client client);

    Optional<Client> updateClient(Long id, Client client);

    void deleteById(Long id);
}
