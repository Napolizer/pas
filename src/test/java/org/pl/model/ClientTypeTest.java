package org.pl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientTypeTest {
    private List<ClientType> clientTypeList;

    @BeforeEach
    void setUp() {
        clientTypeList = Arrays.asList(
                new Basic(),
                new Premium(),
                new Vip()
        );
    }

    @Test
    void testToString() {
        assertEquals(
                "Basic(super=ClientType(factor=1.0, maxRepairs=2, typeName=Basic))",
                clientTypeList.get(0).toString());
        assertEquals(
                "Premium(super=ClientType(factor=0.9, maxRepairs=5, typeName=Premium))",
                clientTypeList.get(1).toString());
        assertEquals(
                "Vip(super=ClientType(factor=0.8, maxRepairs=10, typeName=Vip))",
                clientTypeList.get(2).toString());
    }

    @Test
    void testEquals() {
        for (ClientType clientType : clientTypeList) {
            assertNotEquals(null, clientType);
            assertNotEquals(2, clientType);
            assertNotEquals("2", clientType);
        }
        assertEquals(new Basic(), clientTypeList.get(0));
        assertEquals(new Premium(), clientTypeList.get(1));
        assertEquals(new Vip(), clientTypeList.get(2));
    }
}