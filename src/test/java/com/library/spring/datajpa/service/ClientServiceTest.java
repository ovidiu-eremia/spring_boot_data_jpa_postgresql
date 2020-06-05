package com.library.spring.datajpa.service;

import com.library.spring.datajpa.model.Client;

import com.library.spring.datajpa.repository.ClientRepository;
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
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl underTest;

    @Test
    public void getAllClientsWhenNameNotNull() {
        //given
        String name = "Some name";
        Client client = new Client(name);
        List<Client> clients = Collections.singletonList(client);

        given(clientRepository.findByNameContainingOrderByName(name)).willReturn(clients);

        //when
        List<Client> result = underTest.getAllClients(name);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(client);

        verify(clientRepository).findByNameContainingOrderByName(name);
        verify(clientRepository, times(0)).findAll();
    }

    @Test
    public void getAllClientsWhenNameNull() {
        //given
        Client client = new Client("Some name");
        List<Client> clients = Collections.singletonList(client);

        given(clientRepository.findAll(Sort.by("name"))).willReturn(clients);

        //when
        List<Client> result = underTest.getAllClients(null);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(client);

        verify(clientRepository).findAll(Sort.by("name"));
        verify(clientRepository, times(0)).findByNameContainingOrderByName(any());
    }

    @Test
    public void findById() {
        long id = 3L;

        Client client = new Client("Some name");

        given(clientRepository.findById(id)).willReturn(Optional.of(client));

        Optional<Client> result = underTest.findById(id);

        assertThat(result.get()).isEqualTo(client);

        verify(clientRepository).findById(id);
    }

    @Test
    public void save() {
        //given
        long id = 3L;
        Client client = new Client("Some name");
        given(clientRepository.save(client)).willReturn(client);

        //when
        Client result = underTest.save(client);

        //then
        assertThat(result).isEqualTo(client);
        verify(clientRepository).save(client);
    }

    @Test
    public void deleteById() {
        //given
        long id = 3L;
        Client client = new Client("Some name");
        doNothing().when(clientRepository).deleteById(id);

        //when
        underTest.deleteById(id);

        //then
        verify(clientRepository).deleteById(id);
    }

    @Test
    public void updateClientWhenIdFound() {
        //given
        long id = 3L;
        Client client = new Client("Some name");
        client.setId(id);
        Client updatedClient = new Client("Another name");
        given(clientRepository.findById(id)).willReturn(Optional.of(client));
        given(clientRepository.save(any())).willReturn(updatedClient);

        //when
        Optional<Client> result = underTest.updateClient(id, updatedClient);

        //then
        assertThat(result.get()).isEqualTo(updatedClient);
        verify(clientRepository).findById(id);
        verify(clientRepository).save(eq(client));
    }

    @Test
    public void updateClientWhenIdNotFound() {
        //given
        long id = 3L;
        Client updatedClient = new Client("Another name");
        given(clientRepository.findById(id)).willReturn(Optional.empty());

        //when
        Optional<Client> result = underTest.updateClient(id, updatedClient);

        //then
        assertThat(result).isEqualTo(Optional.empty());
        verify(clientRepository).findById(id);
        verify(clientRepository,times(0)).save(any());
    }
}