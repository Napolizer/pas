package org.pl.gateway.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.gateway.module.model.DateRangeRest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DateRangeTest {
//    ValidatorFactory validatorFactory;
//    Validator validator;
//
//    DateRangeRest validDateRange;
//
//    @BeforeEach
//    void setup() throws ParseException {
//        validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//
//        validDateRange = new DateRangeRest(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("13-02-2020 12:10:10"),
//                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("15-02-2020 12:10:10"));
//    }
//
//    @Test
//    void fieldStartDatePositiveTest() throws ParseException {
//        List<Date> validStartDates = List.of(
//                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("13-02-2020 12:42:51"),
//                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("15-12-2021 17:58:32"),
//                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("11-07-2022 23:10:14")
//        );
//
//        for (var startDate : validStartDates) {
//            validDateRange.setStartDate(startDate);
//            assertEquals(startDate, validDateRange.getStartDate());
//            assertTrue(validator.validate(validDateRange).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldStartDateNegativeTest() {
//        validDateRange.setStartDate(null);
//        assertNull(validDateRange.getStartDate());
//        assertFalse(validator.validate(validDateRange).isEmpty());
//    }
//
//    @Test
//    void fieldEndDatePositiveTest() throws ParseException {
//        List<Date> validEndDates = List.of(
//                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("13-02-2020 12:42:51"),
//                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("15-12-2021 17:58:32"),
//                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("11-07-2022 23:10:14")
//        );
//
//        for (var endDate : validEndDates) {
//            validDateRange.setEndDate(endDate);
//            assertEquals(endDate, validDateRange.getEndDate());
//            assertTrue(validator.validate(validDateRange).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldEndDateNegativeTest() {
//        validDateRange.setEndDate(null);
//        assertNull(validDateRange.getEndDate());
//        assertFalse(validator.validate(validDateRange).isEmpty());
//    }
}
