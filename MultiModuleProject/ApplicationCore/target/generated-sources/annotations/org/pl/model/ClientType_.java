package org.pl.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ClientType.class)
public abstract class ClientType_ {

	public static volatile SingularAttribute<ClientType, Integer> maxRepairs;
	public static volatile SingularAttribute<ClientType, UUID> id;
	public static volatile SingularAttribute<ClientType, Double> factor;
	public static volatile SingularAttribute<ClientType, String> type;

	public static final String MAX_REPAIRS = "maxRepairs";
	public static final String ID = "id";
	public static final String FACTOR = "factor";
	public static final String TYPE = "type";

}

