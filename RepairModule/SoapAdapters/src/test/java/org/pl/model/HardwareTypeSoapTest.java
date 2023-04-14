package org.pl.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.HardwareSoapException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HardwareTypeSoapTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    UUID uuid;
    ComputerSoap validComputer;
    ConsoleSoap validConsole;
    MonitorSoap validMonitor;
    PhoneSoap validPhone;
    List<HardwareTypeSoap> validHardwareTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        uuid = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");

        validComputer = new ComputerSoap(ConditionSoap.FINE);
        validConsole = new ConsoleSoap(ConditionSoap.BAD);
        validMonitor = new MonitorSoap(ConditionSoap.DUSTY);
        validPhone = new PhoneSoap(ConditionSoap.AVERAGE);

        validHardwareTypes = List.of(
                validComputer,
                validConsole,
                validMonitor,
                validPhone
        );
    }

    @Test
    void fieldConditionPositiveTest() {
        List<ConditionSoap> validConditions = List.of(
                ConditionSoap.UNREPAIRABLE,
                ConditionSoap.VERY_BAD,
                ConditionSoap.BAD,
                ConditionSoap.AVERAGE,
                ConditionSoap.DUSTY,
                ConditionSoap.FINE
        );
        for (var condition : validConditions) {
            for (HardwareTypeSoap hardwareType : validHardwareTypes) {
                hardwareType.setCondition(condition);
                assertEquals(condition, hardwareType.getCondition());
                assertTrue(validator.validate(hardwareType).isEmpty());
            }
        }
    }

    @Nested
    class calculateRepairCostTests {
        @Test
        void negativePriceTest() {
            for (HardwareTypeSoap hardwareType : validHardwareTypes) {
                assertThrows(HardwareSoapException.class, () -> hardwareType.calculateRepairCost(-0.01));
            }
        }

        @Test
        void UnrepairableConditionTest() {
            for (HardwareTypeSoap hardwareType : validHardwareTypes) {
                hardwareType.setCondition(ConditionSoap.UNREPAIRABLE);
                assertThrows(HardwareSoapException.class, () -> hardwareType.calculateRepairCost(10.0));
            }
        }

        @Test
        void FineConditionTest() {
            for (HardwareTypeSoap hardwareType : validHardwareTypes) {
                hardwareType.setCondition(ConditionSoap.FINE);
                assertThrows(HardwareSoapException.class, () -> hardwareType.calculateRepairCost(10.0));
            }
        }

        @Nested
        class ComputerRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareSoapException {
                validComputer.setCondition(ConditionSoap.VERY_BAD);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(70.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(210.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareSoapException {
                validComputer.setCondition(ConditionSoap.BAD);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(50.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(150.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareSoapException {
                validComputer.setCondition(ConditionSoap.AVERAGE);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(20.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(60.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareSoapException {
                validComputer.setCondition(ConditionSoap.DUSTY);
                assertEquals(5.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(5.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(5.0, validComputer.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class ConsoleRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareSoapException {
                validConsole.setCondition(ConditionSoap.VERY_BAD);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(90.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(270.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareSoapException {
                validConsole.setCondition(ConditionSoap.BAD);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareSoapException {
                validConsole.setCondition(ConditionSoap.AVERAGE);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(60.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(180.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareSoapException {
                validConsole.setCondition(ConditionSoap.DUSTY);
                assertEquals(100.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(100.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(100.0, validConsole.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class MonitorRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareSoapException {
                validMonitor.setCondition(ConditionSoap.VERY_BAD);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(95.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(285.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareSoapException {
                validMonitor.setCondition(ConditionSoap.BAD);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(90.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(270.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareSoapException {
                validMonitor.setCondition(ConditionSoap.AVERAGE);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareSoapException {
                validMonitor.setCondition(ConditionSoap.DUSTY);
                assertEquals(10.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(10.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(10.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class PhoneRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareSoapException {
                validPhone.setCondition(ConditionSoap.VERY_BAD);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareSoapException {
                validPhone.setCondition(ConditionSoap.BAD);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(50.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(150.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareSoapException {
                validPhone.setCondition(ConditionSoap.AVERAGE);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(20.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(60.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareSoapException {
                validPhone.setCondition(ConditionSoap.DUSTY);
                assertEquals(5.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(5.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(5.0, validPhone.calculateRepairCost(300.0), 0.001);
            }
        }
    }

    @Nested
    class EqualsTests {
        @Test
        void computerEqualsNegativeTest() {
            ComputerSoap computer1 = new ComputerSoap(ConditionSoap.DUSTY);
            ComputerSoap computer2 = new ComputerSoap(ConditionSoap.FINE);
            assertNotEquals(computer1, computer2);
        }

        @Test
        void consoleEqualsNegativeTest() {
            ConsoleSoap console1 = new ConsoleSoap(ConditionSoap.DUSTY);
            ConsoleSoap console2 = new ConsoleSoap(ConditionSoap.FINE);
            assertNotEquals(console1, console2);
        }

        @Test
        void monitorEqualsNegativeTest() {
            MonitorSoap monitor1 = new MonitorSoap(ConditionSoap.DUSTY);
            MonitorSoap monitor2 = new MonitorSoap(ConditionSoap.FINE);

            assertNotEquals(monitor1, monitor2);
        }

        @Test
        void phoneEqualsNegativeTest() {
            PhoneSoap phone1 = new PhoneSoap(ConditionSoap.DUSTY);
            PhoneSoap phone2 = new PhoneSoap(ConditionSoap.FINE);

            assertNotEquals(phone1, phone2);
        }
    }
}