package org.pl.controllers;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
public class HardwareSoapControllerMicroshedIT {
    @Test
    @Order(1)
    public void properlyGetsAllHardware() {
        given()
                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hw=\"http://controllers.pl.org/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <hw:getAllHardware/>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>")
                .header("Content-Type", "text/xml")
                .when()
                .post("/HardwareSoapController")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("text/xml")
                .body("Envelope.Body.getAllHardwareResponse.return.size()", is(equalTo(0)));
    }

    @Test
    @Order(2)
    public void properlyCreatesHardware() {
        given()
                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hw=\"http://controllers.pl.org/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <hw:getAllHardware/>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>")
                .header("Content-Type", "text/xml")
                .when()
                .post("/HardwareSoapController")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("text/xml")
                .body("Envelope.Body.getAllHardwareResponse.return.size()", is(equalTo(0)));

        given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:createHardware>
                                    <arg0 id="8d55eae7-1d03-4d2d-8f39-59f03b57fa1e">
                                        <archive>false</archive>
                                        <price>200</price>
                                        <hardwareType>
                                            <type>Console</type>
                                            <condition>FINE</condition>
                                        </hardwareType>
                                    </arg0>
                                </con:createHardware>
                            </soapenv:Body>
                        </soapenv:Envelope>
                        """)
                .header("Content-Type", "text/xml")
                .when()
                .post("/HardwareSoapController")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("text/xml")
                .body("Envelope.Body.createHardwareResponse.return.archive", equalTo("false"));

        given()
                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hw=\"http://controllers.pl.org/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <hw:getAllHardware/>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>")
                .header("Content-Type", "text/xml")
                .when()
                .post("/HardwareSoapController")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("text/xml")
                .body("Envelope.Body.getAllHardwareResponse.return.size()", is(equalTo(1)));
    }
}