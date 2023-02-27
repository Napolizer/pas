package org.pl.adapter.data.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DateRangeEnt.class)
public abstract class DateRangeEnt_ {

	public static volatile SingularAttribute<DateRangeEnt, Date> endDate;
	public static volatile SingularAttribute<DateRangeEnt, Date> startDate;

	public static final String END_DATE = "endDate";
	public static final String START_DATE = "startDate";

}

