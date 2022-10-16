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