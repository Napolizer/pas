package org.pl.adapter.data.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ClientTypeEnt.class)
public abstract class ClientTypeEnt_ {

	public static volatile SingularAttribute<ClientTypeEnt, Integer> maxRepairs;
	public static volatile SingularAttribute<ClientTypeEnt, UUID> id;
	public static volatile SingularAttribute<ClientTypeEnt, Double> factor;
	public static volatile SingularAttribute<ClientTypeEnt, String> type;

	public static final String MAX_REPAIRS = "maxRepairs";
	public static final String ID = "id";
	public static final String FACTOR = "factor";
	public static final String TYPE = "type";

}

