package org.pl.adapter.data.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HardwareTypeEnt.class)
public abstract class HardwareTypeEnt_ {

	public static volatile SingularAttribute<HardwareTypeEnt, ConditionEnt> condition;
	public static volatile SingularAttribute<HardwareTypeEnt, UUID> id;
	public static volatile SingularAttribute<HardwareTypeEnt, String> type;

	public static final String CONDITION = "condition";
	public static final String ID = "id";
	public static final String TYPE = "type";

}

