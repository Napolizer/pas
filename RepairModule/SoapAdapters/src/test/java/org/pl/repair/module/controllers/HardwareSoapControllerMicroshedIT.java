package org.pl.repair.module.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;

import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HardwareSoapControllerMicroshedIT {
    @Test
    @Order(1)
    public void properlyGetsAllHardware() {
        given()
                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hw=\"http://controllers.module.repair.pl.org/\">\n" +
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
                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hw=\"http://controllers.module.repair.pl.org/\">\n" +
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
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
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
                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hw=\"http://controllers.module.repair.pl.org/\">\n" +
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

    @Test
    @Order(3)
    public void properlyGetsHardwareById() {
        UUID createdHardwareId = UUID.fromString(given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:createHardware>
                                    <arg0 id="8d55eae7-1d03-4d2d-8f39-59f03b57fa1e">
                                        <archive>false</archive>
                                        <price>200</price>
                                        <hardwareType>
                                            <type>Monitor</type>
                                            <condition>BAD</condition>
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
                .body("Envelope.Body.createHardwareResponse.return.archive", equalTo("false"))
                .extract()
                .path("Envelope.Body.createHardwareResponse.return.@id"));

        given()
            .body("""
                    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                        <soapenv:Header/>
                        <soapenv:Body>
                            <con:getHardwareById>
                                <arg0>""" + createdHardwareId + """
                            </arg0>
                            </con:getHardwareById>
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
            .body("Envelope.Body.getHardwareByIdResponse.return.archive", equalTo("false"))
            .body("Envelope.Body.getHardwareByIdResponse.return.@id", equalTo(createdHardwareId.toString()));
    }

    @Test
    @Order(4)
    public void properlyUpdatesHardware() {
            UUID createdHardwareId = UUID.fromString(given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:createHardware>
                                    <arg0 id="8d55eae7-1d03-4d2d-8f39-59f03b57fa1e">
                                        <archive>false</archive>
                                        <price>200</price>
                                        <hardwareType>
                                            <type>Monitor</type>
                                            <condition>BAD</condition>
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
                .body("Envelope.Body.createHardwareResponse.return.archive", equalTo("false"))
                .extract()
                .path("Envelope.Body.createHardwareResponse.return.@id"));

            given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:updateHardware>
                                    <arg0 id=""" + '"' + createdHardwareId + """
                                        ">
                                        <archive>true</archive>
                                        <price>300</price>
                                        <hardwareType>
                                            <type>Monitor</type>
                                            <condition>BAD</condition>
                                        </hardwareType>
                                    </arg0>
                                </con:updateHardware>
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
                .body("Envelope.Body.updateHardwareResponse.return.archive", equalTo("true"))
                .body("Envelope.Body.updateHardwareResponse.return.@id", equalTo(createdHardwareId.toString()))
                .body("Envelope.Body.updateHardwareResponse.return.price", equalTo("300"));
    }

    @Test
    @Order(5)
    public void properlyDeletesHardware() {
        UUID createdHardwareId = UUID.fromString(given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:createHardware>
                                    <arg0 id="8d55eae7-1d03-4d2d-8f39-59f03b57fa1e">
                                        <archive>false</archive>
                                        <price>200</price>
                                        <hardwareType>
                                            <type>Monitor</type>
                                            <condition>BAD</condition>
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
                .body("Envelope.Body.createHardwareResponse.return.archive", equalTo("false"))
                .extract()
                .path("Envelope.Body.createHardwareResponse.return.@id"));

        given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:deleteHardware>
                                    <arg0>""" + createdHardwareId + """
                                    </arg0>
                                </con:deleteHardware>
                            </soapenv:Body>
                        </soapenv:Envelope>
                        """)
                .header("Content-Type", "text/xml")
                .when()
                .post("/HardwareSoapController")
                .then()
                .assertThat()
                .statusCode(200);

            given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:getHardwareById>
                                    <arg0>""" + createdHardwareId + """
                                </arg0>
                                </con:getHardwareById>
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
                .body("Envelope.Body.getHardwareByIdResponse.return.archive", is(not(equalTo("false"))));
    }

    @Test
    @Order(6)
    public void failsToDeleteHardware() {
        given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:deleteHardware>
                                    <arg0>""" + UUID.randomUUID() + """
                                    </arg0>
                                </con:deleteHardware>
                            </soapenv:Body>
                        </soapenv:Envelope>
                        """)
                .header("Content-Type", "text/xml")
                .when()
                .post("/HardwareSoapController")
                .then()
                .assertThat()
                .statusCode(500);
    }

    @Test
    @Order(7)
    public void failsToUpdateHardware() {
        given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:updateHardware>
                                    <arg0 id="8d55eae7-1d03-4d2d-8f39-59f03b57fa1e">
                                        <archive>true</archive>
                                        <price>300</price>
                                        <hardwareType>
                                            <type>Monitor</type>
                                            <condition>BAD</condition>
                                        </hardwareType>
                                    </arg0>
                                </con:updateHardware>
                            </soapenv:Body>
                        </soapenv:Envelope>
                        """)
                .header("Content-Type", "text/xml")
                .when()
                .post("/HardwareSoapController")
                .then()
                .assertThat()
                .statusCode(500);
    }

    @Test
    @Order(8)
    public void failsToCreateHardware() {
        given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:createHardware>
                                    <arg0>
                                        <archive>false</archive>
                                        <price>abc</price>
                                        <hardwareType>
                                            <type>Monitor</type>
                                            <condition>BAD</condition>
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
                .statusCode(500);
    }

    @Test
    @Order(9)
    public void failsToGetHardwareById() {
        given()
                .body("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controllers.module.repair.pl.org/">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:getHardwareById>
                                    <arg0>123</arg0>
                                </con:getHardwareById>
                            </soapenv:Body>
                        </soapenv:Envelope>
                        """)
                .header("Content-Type", "text/xml")
                .when()
                .post("/HardwareSoapController")
                .then()
                .assertThat()
                .statusCode(500);
    }
}