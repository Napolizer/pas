package org.pl.model;

import lombok.Builder;
import lombok.Data;
import org.pl.exceptions.ClientException;

@Data
@Builder
public class Client implements Entity {
    private boolean archive;
    private double balance;
    private String firstName;
    private String lastName;
    private int personalId;
    private String phoneNumber;
    private ClientType clientType;
    private Address address;

    public double calculateDiscount(int price) throws ClientException {
        return getClientType().calculateDiscount(price);
    }

    public void changeBalance(double amount) {
        setBalance(getBalance() + amount);
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public int getID() {
        return personalId;
    }
}
