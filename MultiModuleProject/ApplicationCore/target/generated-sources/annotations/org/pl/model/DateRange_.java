package org.pl.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DateRange.class)
public abstract class DateRange_ {

	public static volatile SingularAttribute<DateRange, Date> endDate;
	public static volatile SingularAttribute<DateRange, Date> startDate;

	public static final String END_DATE = "endDate";
	public static final String START_DATE = "startDate";

}

