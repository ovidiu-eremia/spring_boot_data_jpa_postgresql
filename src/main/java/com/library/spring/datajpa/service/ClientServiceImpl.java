package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.Client;

import com.library.spring.datajpa.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients(String name) {
        List<Client> clients = new ArrayList<Client>();

        if (name != null) {
            clientRepository.findByNameContainingOrderByName(name).forEach(clients::add);
        } else {
            clientRepository.findAll(Sort.by("name")).forEach(clients::add);
        }

        return clients;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Optional<Client> updateClient(Long id, Client client) {
        Optional<Client> clientData = clientRepository.findById(id);

        if (clientData.isPresent()) {
            Client _client = clientData.get();
            _client.setName(client.getName());
            return Optional.of(clientRepository.save(_client));
        } else {
            return Optional.empty();
        }
    }

}
