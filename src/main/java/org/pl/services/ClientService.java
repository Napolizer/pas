package org.pl.services;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Client;
import org.pl.repositories.ClientRepository;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client add(String firstName, String lastName, String phoneNumber, Address address) throws RepositoryException {
        Client client = Client.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
        clientRepository.add(client);
        return client;
    }

    public Client add(String firstName, String lastName, String phoneNumber, String city, String number, String street) throws RepositoryException {
        Address address = Address.builder()
                .city(city)
                .number(number)
                .street(street)
                .build();
        Client client = Client.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
        clientRepository.add(client);
        return client;
    }

    public Client get(int id) throws RepositoryException {
        return clientRepository.get(id);
    }

    public int getArchiveSize() throws RepositoryException {
        return clientRepository.getSize(false);
    }

    public double getClientBalance(int id) throws RepositoryException {
        return clientRepository.get(id).getBalance();
    }

    public int getPresentSize() throws RepositoryException {
        return clientRepository.getSize(true);
    }

    public String getInfo(int id) throws RepositoryException {
        return clientRepository.get(id).toString();
    }

    public String getReport() {
        return clientRepository.toString();
    }

    public void remove(int id) throws RepositoryException {
        clientRepository.archivise(id);
    }
}
