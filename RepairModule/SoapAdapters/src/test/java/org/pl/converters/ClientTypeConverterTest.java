package org.pl.converters;

import org.junit.jupiter.api.Test;
import org.pl.model.Basic;
import org.pl.model.BasicSoap;
import org.pl.model.ClientType;
import org.pl.model.ClientTypeSoap;

import static org.junit.jupiter.api.Assertions.*;


public class ClientTypeConverterTest {
    private final ClientTypeConverter clientTypeConverter = new ClientTypeConverter();
    @Test
    void convertClientTypeFromDomainToSoapModelTest() {
        ClientType basicClientType = new Basic();
        ClientTypeSoap basicClientTypeSoap = clientTypeConverter.convert(basicClientType);
        assertInstanceOf(BasicSoap.class, basicClientTypeSoap);
        assertEquals(basicClientType.getType(), basicClientTypeSoap.getType());
        assertEquals(basicClientType.getFactor(), basicClientTypeSoap.getFactor());
        assertEquals(basicClientType.getMaxRepairs(), basicClientTypeSoap.getMaxRepairs());
    }

    @Test
    void convertClientTypeFromSoapToDomainModelTest() {
        ClientTypeSoap basicClientTypeSoap = new BasicSoap();
        ClientType basicClientType = clientTypeConverter.convert(basicClientTypeSoap);
        assertInstanceOf(Basic.class, basicClientType);
        assertEquals(basicClientTypeSoap.getType(), basicClientType.getType());
        assertEquals(basicClientTypeSoap.getFactor(), basicClientType.getFactor());
        assertEquals(basicClientTypeSoap.getMaxRepairs(), basicClientType.getMaxRepairs());
    }
}
