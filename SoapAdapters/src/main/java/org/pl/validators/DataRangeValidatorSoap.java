package org.pl.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.xml.bind.annotation.XmlTransient;
import org.pl.annotations.ValidDateRangeSoap;
import org.pl.model.DateRangeSoap;

@XmlTransient
public class DataRangeValidatorSoap implements ConstraintValidator<ValidDateRangeSoap, DateRangeSoap> {
    @Override
    public boolean isValid(DateRangeSoap value, ConstraintValidatorContext context) {
        if (value.getEndDate() == null) {
            return true;
        } if (value.getStartDate() == null) {
            return false;
        }
        return value.getStartDate().before(value.getEndDate());
    }
}
