package org.pl.adapter.data.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AddressEnt.class)
public abstract class AddressEnt_ {

	public static volatile SingularAttribute<AddressEnt, String> number;
	public static volatile SingularAttribute<AddressEnt, String> city;
	public static volatile SingularAttribute<AddressEnt, String> street;

	public static final String NUMBER = "number";
	public static final String CITY = "city";
	public static final String STREET = "street";

}

