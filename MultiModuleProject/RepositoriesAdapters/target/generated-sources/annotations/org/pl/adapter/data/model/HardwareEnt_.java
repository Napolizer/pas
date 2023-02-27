package org.pl.adapter.data.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HardwareEnt.class)
public abstract class HardwareEnt_ {

	public static volatile SingularAttribute<HardwareEnt, HardwareTypeEnt> hardwareTypeEnt;
	public static volatile SingularAttribute<HardwareEnt, Integer> price;
	public static volatile SingularAttribute<HardwareEnt, Boolean> archive;
	public static volatile SingularAttribute<HardwareEnt, UUID> id;

	public static final String HARDWARE_TYPE_ENT = "hardwareTypeEnt";
	public static final String PRICE = "price";
	public static final String ARCHIVE = "archive";
	public static final String ID = "id";

}

