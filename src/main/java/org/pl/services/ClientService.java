package org.pl.services;

import org.pl.model.Address;
import org.pl.model.Client;

public class ClientService {
    private ClientRepository clientRepository;

    public Client add(String firstName, String lastName, String phoneNumber, Address address) {
        Client client = Client.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
        clientRepository.add(client);
        return client;
    }

    public Client add(String firstName, String lastName, String phoneNumber, String city, String number, String street) {
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

    public Client get(int ID) {
        return clientRepository.get(ID);
    }

    public int getArchiveSize() {
        return clientRepository.getSize(false);
    }

    public float getClientBalance(int ID) {
        return clientRepository.get(ID).getBalance();
    }

    public int getPresentSize() {
        return clientRepository.getSize(true);
    }

    public String getInfo(int ID) {
        return clientRepository.get(ID).toString();
    }

    public String getReport() {
        return clientRepository.toString();
    }

    public void remove(int ID) {
        clientRepository.archivise(ID);
    }
}
