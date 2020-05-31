package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAllClients(String name);

    Optional<Client> findById(long id);

    Client save(Client client);

    Optional<Client> updateClient(long id, Client client);

    void deleteById(long id);
}
