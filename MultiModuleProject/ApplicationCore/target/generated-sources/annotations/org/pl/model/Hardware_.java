package org.pl.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Hardware.class)
public abstract class Hardware_ {

	public static volatile SingularAttribute<Hardware, Integer> price;
	public static volatile SingularAttribute<Hardware, HardwareType> hardwareType;
	public static volatile SingularAttribute<Hardware, Boolean> archive;
	public static volatile SingularAttribute<Hardware, UUID> id;

	public static final String PRICE = "price";
	public static final String HARDWARE_TYPE = "hardwareType";
	public static final String ARCHIVE = "archive";
	public static final String ID = "id";

}

