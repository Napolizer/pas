package org.pl.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HardwareType.class)
public abstract class HardwareType_ {

	public static volatile SingularAttribute<HardwareType, Condition> condition;
	public static volatile SingularAttribute<HardwareType, UUID> id;
	public static volatile SingularAttribute<HardwareType, String> type;

	public static final String CONDITION = "condition";
	public static final String ID = "id";
	public static final String TYPE = "type";

}

