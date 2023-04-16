package org.pl.repair.module.adapter.data;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.pl.repair.module.adapter.data.model.*;
import org.pl.repair.module.adapter.data.model.exceptions.HardwareEntException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HardwareTypeEntTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    UUID uuid;
    ComputerEnt validComputer;
    ConsoleEnt validConsole;
    MonitorEnt validMonitor;
    PhoneEnt validPhone;
    List<HardwareTypeEnt> validHardwareTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        uuid = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");

        validComputer = new ComputerEnt(ConditionEnt.FINE);
        validConsole = new ConsoleEnt(ConditionEnt.BAD);
        validMonitor = new MonitorEnt(ConditionEnt.DUSTY);
        validPhone = new PhoneEnt(ConditionEnt.AVERAGE);

        validHardwareTypes = List.of(
                validComputer,
                validConsole,
                validMonitor,
                validPhone
        );
    }

    @Test
    void fieldConditionPositiveTest() {
        List<ConditionEnt> validConditions = List.of(
                ConditionEnt.UNREPAIRABLE,
                ConditionEnt.VERY_BAD,
                ConditionEnt.BAD,
                ConditionEnt.AVERAGE,
                ConditionEnt.DUSTY,
                ConditionEnt.FINE
        );
        for (var condition : validConditions) {
            for (HardwareTypeEnt hardwareType : validHardwareTypes) {
                hardwareType.setCondition(condition);
                assertEquals(condition, hardwareType.getCondition());
                assertTrue(validator.validate(hardwareType).isEmpty());
            }
        }
        for (HardwareTypeEnt hardwareType : validHardwareTypes) {
            hardwareType.setCondition(null);
            assertNull(hardwareType.getCondition());
            assertTrue(validator.validate(hardwareType).isEmpty());
        }
    }

    @Nested
    class calculateRepairCostTests {
        @Test
        void negativePriceTest() {
            for (HardwareTypeEnt hardwareType : validHardwareTypes) {
                assertThrows(HardwareEntException.class, () -> hardwareType.calculateRepairCost(-0.01));
            }
        }

        @Test
        void UnrepairableConditionTest() {
            for (HardwareTypeEnt hardwareType : validHardwareTypes) {
                hardwareType.setCondition(ConditionEnt.UNREPAIRABLE);
                assertThrows(HardwareEntException.class, () -> hardwareType.calculateRepairCost(10.0));
            }
        }

        @Test
        void FineConditionTest() {
            for (HardwareTypeEnt hardwareType : validHardwareTypes) {
                hardwareType.setCondition(ConditionEnt.FINE);
                assertThrows(HardwareEntException.class, () -> hardwareType.calculateRepairCost(10.0));
            }
        }

        @Nested
        class ComputerRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareEntException {
                validComputer.setCondition(ConditionEnt.VERY_BAD);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(70.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(210.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareEntException {
                validComputer.setCondition(ConditionEnt.BAD);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(50.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(150.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareEntException {
                validComputer.setCondition(ConditionEnt.AVERAGE);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(20.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(60.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareEntException {
                validComputer.setCondition(ConditionEnt.DUSTY);
                assertEquals(5.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(5.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(5.0, validComputer.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class ConsoleRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareEntException {
                validConsole.setCondition(ConditionEnt.VERY_BAD);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(90.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(270.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareEntException {
                validConsole.setCondition(ConditionEnt.BAD);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareEntException {
                validConsole.setCondition(ConditionEnt.AVERAGE);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(60.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(180.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareEntException {
                validConsole.setCondition(ConditionEnt.DUSTY);
                assertEquals(100.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(100.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(100.0, validConsole.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class MonitorRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareEntException {
                validMonitor.setCondition(ConditionEnt.VERY_BAD);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(95.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(285.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareEntException {
                validMonitor.setCondition(ConditionEnt.BAD);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(90.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(270.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareEntException {
                validMonitor.setCondition(ConditionEnt.AVERAGE);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareEntException {
                validMonitor.setCondition(ConditionEnt.DUSTY);
                assertEquals(10.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(10.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(10.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class PhoneRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareEntException {
                validPhone.setCondition(ConditionEnt.VERY_BAD);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareEntException {
                validPhone.setCondition(ConditionEnt.BAD);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(50.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(150.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareEntException {
                validPhone.setCondition(ConditionEnt.AVERAGE);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(20.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(60.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareEntException {
                validPhone.setCondition(ConditionEnt.DUSTY);
                assertEquals(5.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(5.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(5.0, validPhone.calculateRepairCost(300.0), 0.001);
            }
        }
    }

    @Nested
    class EqualsTests {
        final UUID differentUuid = UUID.fromString("6fc03087-d265-11e7-b8c6-83e29cd24f4c");

        @Test
        void computerEqualsPositiveTest() {
            ComputerEnt computer1 = ComputerEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.DUSTY).build();
            ComputerEnt computer2 = ComputerEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.FINE).build();

            assertEquals(computer1, computer2);
        }

        @Test
        void computerEqualsNegativeTest() {
            ComputerEnt computer1 = ComputerEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.DUSTY).build();
            ComputerEnt computer2 = ComputerEnt.builder()
                    .id(differentUuid)
                    .condition(ConditionEnt.FINE).build();

            assertNotEquals(computer1, computer2);
        }

        @Test
        void consoleEqualsPositiveTest() {
            ConsoleEnt console1 = ConsoleEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.DUSTY).build();
            ConsoleEnt console2 = ConsoleEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.FINE).build();

            assertEquals(console1, console2);
        }

        @Test
        void consoleEqualsNegativeTest() {
            ConsoleEnt console1 = ConsoleEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.DUSTY).build();
            ConsoleEnt console2 = ConsoleEnt.builder()
                    .id(differentUuid)
                    .condition(ConditionEnt.FINE).build();

            assertNotEquals(console1, console2);
        }

        @Test
        void monitorEqualsPositiveTest() {
            MonitorEnt monitor1 = MonitorEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.DUSTY).build();
            MonitorEnt monitor2 = MonitorEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.FINE).build();

            assertEquals(monitor1, monitor2);
        }

        @Test
        void monitorEqualsNegativeTest() {
            MonitorEnt monitor1 = MonitorEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.DUSTY).build();
            MonitorEnt monitor2 = MonitorEnt.builder()
                    .id(differentUuid)
                    .condition(ConditionEnt.FINE).build();

            assertNotEquals(monitor1, monitor2);
        }

        @Test
        void phoneEqualsPositiveTest() {
            PhoneEnt phone1 = PhoneEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.DUSTY).build();
            PhoneEnt phone2 = PhoneEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.FINE).build();

            assertEquals(phone1, phone2);
        }

        @Test
        void phoneEqualsNegativeTest() {
            PhoneEnt phone1 = PhoneEnt.builder()
                    .id(uuid)
                    .condition(ConditionEnt.DUSTY).build();
            PhoneEnt phone2 = PhoneEnt.builder()
                    .id(differentUuid)
                    .condition(ConditionEnt.FINE).build();

            assertNotEquals(phone1, phone2);
        }
    }
}