package org.pl.adapter.data.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RepairEnt.class)
public abstract class RepairEnt_ {

	public static volatile SingularAttribute<RepairEnt, ClientEnt> clientEnt;
	public static volatile SingularAttribute<RepairEnt, DateRangeEnt> dateRangeEnt;
	public static volatile SingularAttribute<RepairEnt, Boolean> archive;
	public static volatile SingularAttribute<RepairEnt, UUID> id;
	public static volatile SingularAttribute<RepairEnt, HardwareEnt> hardwareEnt;

	public static final String CLIENT_ENT = "clientEnt";
	public static final String DATE_RANGE_ENT = "dateRangeEnt";
	public static final String ARCHIVE = "archive";
	public static final String ID = "id";
	public static final String HARDWARE_ENT = "hardwareEnt";

}

