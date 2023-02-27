package org.pl.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ {

	public static volatile SingularAttribute<Client, clientAccessType> clientAccessType;
	public static volatile SingularAttribute<Client, String> firstName;
	public static volatile SingularAttribute<Client, String> lastName;
	public static volatile SingularAttribute<Client, String> password;
	public static volatile SingularAttribute<Client, String> phoneNumber;
	public static volatile SingularAttribute<Client, ClientType> clientType;
	public static volatile SingularAttribute<Client, Address> address;
	public static volatile SingularAttribute<Client, Double> balance;
	public static volatile SingularAttribute<Client, Boolean> archive;
	public static volatile SingularAttribute<Client, UUID> id;
	public static volatile SingularAttribute<Client, String> username;

	public static final String CLIENT_ACCESS_TYPE = "clientAccessType";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String CLIENT_TYPE = "clientType";
	public static final String ADDRESS = "address";
	public static final String BALANCE = "balance";
	public static final String ARCHIVE = "archive";
	public static final String ID = "id";
	public static final String USERNAME = "username";

}

