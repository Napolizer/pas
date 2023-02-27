package org.pl.adapter.data.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ClientEnt.class)
public abstract class ClientEnt_ {

	public static volatile SingularAttribute<ClientEnt, clientAccessTypeEnt> clientAccessType;
	public static volatile SingularAttribute<ClientEnt, String> firstName;
	public static volatile SingularAttribute<ClientEnt, String> lastName;
	public static volatile SingularAttribute<ClientEnt, String> password;
	public static volatile SingularAttribute<ClientEnt, String> phoneNumber;
	public static volatile SingularAttribute<ClientEnt, Double> balance;
	public static volatile SingularAttribute<ClientEnt, ClientTypeEnt> clientTypeEnt;
	public static volatile SingularAttribute<ClientEnt, AddressEnt> addressEnt;
	public static volatile SingularAttribute<ClientEnt, Boolean> archive;
	public static volatile SingularAttribute<ClientEnt, UUID> id;
	public static volatile SingularAttribute<ClientEnt, String> username;

	public static final String CLIENT_ACCESS_TYPE = "clientAccessType";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String BALANCE = "balance";
	public static final String CLIENT_TYPE_ENT = "clientTypeEnt";
	public static final String ADDRESS_ENT = "addressEnt";
	public static final String ARCHIVE = "archive";
	public static final String ID = "id";
	public static final String USERNAME = "username";

}

