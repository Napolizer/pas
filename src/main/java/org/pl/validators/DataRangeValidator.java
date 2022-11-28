package org.pl.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pl.annotations.ValidDateRange;
import org.pl.model.DateRange;


public class DataRangeValidator implements ConstraintValidator<ValidDateRange, DateRange> {
    @Override
    public boolean isValid(DateRange value, ConstraintValidatorContext context) {
        if (value.getEndDate() == null) {
            return true;
        } if (value.getStartDate() == null) {
            return false;
        }
        return value.getStartDate().before(value.getEndDate());
    }
}
