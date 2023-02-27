package org.pl.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Repair.class)
public abstract class Repair_ {

	public static volatile SingularAttribute<Repair, DateRange> dateRange;
	public static volatile SingularAttribute<Repair, Client> client;
	public static volatile SingularAttribute<Repair, Boolean> archive;
	public static volatile SingularAttribute<Repair, UUID> id;
	public static volatile SingularAttribute<Repair, Hardware> hardware;

	public static final String DATE_RANGE = "dateRange";
	public static final String CLIENT = "client";
	public static final String ARCHIVE = "archive";
	public static final String ID = "id";
	public static final String HARDWARE = "hardware";

}

