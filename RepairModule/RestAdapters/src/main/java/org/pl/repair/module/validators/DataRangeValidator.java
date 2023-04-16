package org.pl.repair.module.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pl.repair.module.annotations.ValidDateRange;
import org.pl.repair.module.model.DateRangeRest;


public class DataRangeValidator implements ConstraintValidator<ValidDateRange, DateRangeRest> {
    @Override
    public boolean isValid(DateRangeRest value, ConstraintValidatorContext context) {
        if (value.getEndDate() == null) {
            return true;
        } if (value.getStartDate() == null) {
            return false;
        }
        return value.getStartDate().before(value.getEndDate());
    }
}
