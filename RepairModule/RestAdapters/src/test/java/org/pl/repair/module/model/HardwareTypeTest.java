package org.pl.repair.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.pl.repair.module.model.*;
import org.pl.repair.module.model.exceptions.HardwareRestException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HardwareTypeTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    UUID uuid;
    ComputerRest validComputer;
    ConsoleRest validConsole;
    MonitorRest validMonitor;
    PhoneRest validPhone;
    List<HardwareTypeRest> validHardwareTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        uuid = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");

        validComputer = new ComputerRest(ConditionRest.FINE);
        validConsole = new ConsoleRest(ConditionRest.BAD);
        validMonitor = new MonitorRest(ConditionRest.DUSTY);
        validPhone = new PhoneRest(ConditionRest.AVERAGE);

        validHardwareTypes = List.of(
                validComputer,
                validConsole,
                validMonitor,
                validPhone
        );
    }

    @Test
    void fieldConditionPositiveTest() {
        List<ConditionRest> validConditions = List.of(
                ConditionRest.UNREPAIRABLE,
                ConditionRest.VERY_BAD,
                ConditionRest.BAD,
                ConditionRest.AVERAGE,
                ConditionRest.DUSTY,
                ConditionRest.FINE
        );
        for (var condition : validConditions) {
            for (HardwareTypeRest hardwareType : validHardwareTypes) {
                hardwareType.setCondition(condition);
                assertEquals(condition, hardwareType.getCondition());
                assertTrue(validator.validate(hardwareType).isEmpty());
            }
        }
    }

    @Test
    void fieldConditionNegativeTest() {
        for (HardwareTypeRest hardwareType : validHardwareTypes) {
            hardwareType.setCondition(null);
            assertNull(hardwareType.getCondition());
            assertFalse(validator.validate(hardwareType).isEmpty());
        }
    }

    @Nested
    class calculateRepairCostTests {
        @Test
        void negativePriceTest() {
            for (HardwareTypeRest hardwareType : validHardwareTypes) {
                assertThrows(HardwareRestException.class, () -> {
                    hardwareType.calculateRepairCost(-0.01);
                });
            }
        }

        @Test
        void UnrepairableConditionTest() {
            for (HardwareTypeRest hardwareType : validHardwareTypes) {
                hardwareType.setCondition(ConditionRest.UNREPAIRABLE);
                assertThrows(HardwareRestException.class, () -> {
                    hardwareType.calculateRepairCost(10.0);
                });
            }
        }

        @Test
        void FineConditionTest() {
            for (HardwareTypeRest hardwareType : validHardwareTypes) {
                hardwareType.setCondition(ConditionRest.FINE);
                assertThrows(HardwareRestException.class, () -> {
                    hardwareType.calculateRepairCost(10.0);
                });
            }
        }

        @Nested
        class ComputerRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareRestException {
                validComputer.setCondition(ConditionRest.VERY_BAD);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(70.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(210.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareRestException {
                validComputer.setCondition(ConditionRest.BAD);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(50.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(150.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareRestException {
                validComputer.setCondition(ConditionRest.AVERAGE);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(20.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(60.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareRestException {
                validComputer.setCondition(ConditionRest.DUSTY);
                assertEquals(5.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(5.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(5.0, validComputer.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class ConsoleRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareRestException {
                validConsole.setCondition(ConditionRest.VERY_BAD);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(90.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(270.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareRestException {
                validConsole.setCondition(ConditionRest.BAD);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareRestException {
                validConsole.setCondition(ConditionRest.AVERAGE);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(60.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(180.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareRestException {
                validConsole.setCondition(ConditionRest.DUSTY);
                assertEquals(100.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(100.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(100.0, validConsole.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class MonitorRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareRestException {
                validMonitor.setCondition(ConditionRest.VERY_BAD);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(95.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(285.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareRestException {
                validMonitor.setCondition(ConditionRest.BAD);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(90.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(270.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareRestException {
                validMonitor.setCondition(ConditionRest.AVERAGE);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareRestException {
                validMonitor.setCondition(ConditionRest.DUSTY);
                assertEquals(10.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(10.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(10.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class PhoneRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareRestException {
                validPhone.setCondition(ConditionRest.VERY_BAD);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareRestException {
                validPhone.setCondition(ConditionRest.BAD);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(50.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(150.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareRestException {
                validPhone.setCondition(ConditionRest.AVERAGE);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(20.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(60.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareRestException {
                validPhone.setCondition(ConditionRest.DUSTY);
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
            ComputerRest computer1 = ComputerRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.DUSTY).build();
            ComputerRest computer2 = ComputerRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.FINE).build();

            assertEquals(computer1, computer2);
        }

        @Test
        void computerEqualsNegativeTest() {
            ComputerRest computer1 = ComputerRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.DUSTY).build();
            ComputerRest computer2 = ComputerRest.builder()
                    .id(differentUuid)
                    .condition(ConditionRest.FINE).build();

            assertNotEquals(computer1, computer2);
        }

        @Test
        void consoleEqualsPositiveTest() {
            ConsoleRest console1 = ConsoleRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.DUSTY).build();
            ConsoleRest console2 = ConsoleRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.FINE).build();

            assertEquals(console1, console2);
        }

        @Test
        void consoleEqualsNegativeTest() {
            ConsoleRest console1 = ConsoleRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.DUSTY).build();
            ConsoleRest console2 = ConsoleRest.builder()
                    .id(differentUuid)
                    .condition(ConditionRest.FINE).build();

            assertNotEquals(console1, console2);
        }

        @Test
        void monitorEqualsPositiveTest() {
            MonitorRest monitor1 = MonitorRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.DUSTY).build();
            MonitorRest monitor2 = MonitorRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.FINE).build();

            assertEquals(monitor1, monitor2);
        }

        @Test
        void monitorEqualsNegativeTest() {
            MonitorRest monitor1 = MonitorRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.DUSTY).build();
            MonitorRest monitor2 = MonitorRest.builder()
                    .id(differentUuid)
                    .condition(ConditionRest.FINE).build();

            assertNotEquals(monitor1, monitor2);
        }

        @Test
        void phoneEqualsPositiveTest() {
            PhoneRest phone1 = PhoneRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.DUSTY).build();
            PhoneRest phone2 = PhoneRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.FINE).build();

            assertEquals(phone1, phone2);
        }

        @Test
        void phoneEqualsNegativeTest() {
            PhoneRest phone1 = PhoneRest.builder()
                    .id(uuid)
                    .condition(ConditionRest.DUSTY).build();
            PhoneRest phone2 = PhoneRest.builder()
                    .id(differentUuid)
                    .condition(ConditionRest.FINE).build();

            assertNotEquals(phone1, phone2);
        }
    }
}