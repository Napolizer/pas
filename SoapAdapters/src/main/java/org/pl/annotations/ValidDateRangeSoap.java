package org.pl.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.xml.bind.annotation.XmlTransient;
import org.pl.validators.DataRangeValidatorSoap;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DataRangeValidatorSoap.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@XmlTransient
public @interface ValidDateRangeSoap {
    String message() default "Start date must be before end date";
    Class <?>[] groups() default {};
    Class <?extends Payload>[] payload() default { };
}
