package org.pl.converters;

import org.junit.jupiter.api.Test;
import org.pl.model.DateRange;
import org.pl.model.DateRangeSoap;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateRangeConverterTest {
    private final DateRangeConverter dateRangeConverter = new DateRangeConverter();

    @Test
    void convertDateRangeFromDomainToSoapModelTest() {
        DateRange dateRange = new DateRange(new Date(), new Date());
        DateRangeSoap dateRangeSoap = dateRangeConverter.convert(dateRange);
        assertInstanceOf(DateRangeSoap.class, dateRangeSoap);
        assertEquals(dateRange.getStartDate(), dateRangeSoap.getStartDate());
        assertEquals(dateRange.getEndDate(), dateRangeSoap.getEndDate());
    }

    @Test
    void convertDateRangeFromSoapToDomainModelTest() {
        DateRangeSoap dateRangeSoap = new DateRangeSoap(new Date(), new Date());
        DateRange dateRange = dateRangeConverter.convert(dateRangeSoap);
        assertInstanceOf(DateRange.class, dateRange);
        assertEquals(dateRangeSoap.getStartDate(), dateRange.getStartDate());
        assertEquals(dateRangeSoap.getEndDate(), dateRange.getEndDate());
    }
}