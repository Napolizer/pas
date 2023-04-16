package org.pl.repair.module.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.pl.repair.module.validators.DataRangeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DataRangeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default "Start date must be before end date";
    Class <?>[] groups() default {};
    Class <?extends Payload>[] payload() default { };
}
