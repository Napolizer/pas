package org.pl.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.HardwareException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HardwareTypeTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    UUID uuid;
    Computer validComputer;
    Console validConsole;
    Monitor validMonitor;
    Phone validPhone;
    List<HardwareType> validHardwareTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        uuid = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");

        validComputer = Computer.builder()
                .condition(Condition.FINE)
                .build();
        validConsole = Console.builder()
                .condition(Condition.BAD)
                .build();
        validMonitor = Monitor.builder()
                .condition(Condition.DUSTY)
                .build();
        validPhone = Phone.builder()
                .condition(Condition.AVERAGE)
                .build();

        validHardwareTypes = List.of(
                validComputer,
                validConsole,
                validMonitor,
                validPhone
        );
    }

    @Test
    void fieldConditionPositiveTest() {
        List<Condition> validConditions = List.of(
                Condition.UNREPAIRABLE,
                Condition.VERY_BAD,
                Condition.BAD,
                Condition.AVERAGE,
                Condition.DUSTY,
                Condition.FINE
        );
        for (var condition : validConditions) {
            for (HardwareType hardwareType : validHardwareTypes) {
                hardwareType.setCondition(condition);
                assertEquals(condition, hardwareType.getCondition());
                assertTrue(validator.validate(hardwareType).isEmpty());
            }
        }
    }

    @Test
    void fieldConditionNegativeTest() {
        for (HardwareType hardwareType : validHardwareTypes) {
            hardwareType.setCondition(null);
            assertNull(hardwareType.getCondition());
            assertFalse(validator.validate(hardwareType).isEmpty());
        }
    }

    @Nested
    class calculateRepairCostTests {
        @Test
        void negativePriceTest() {
            for (HardwareType hardwareType : validHardwareTypes) {
                assertThrows(HardwareException.class, () -> {
                    hardwareType.calculateRepairCost(-0.01);
                });
            }
        }

        @Test
        void UnrepairableConditionTest() {
            for (HardwareType hardwareType : validHardwareTypes) {
                hardwareType.setCondition(Condition.UNREPAIRABLE);
                assertThrows(HardwareException.class, () -> {
                    hardwareType.calculateRepairCost(10.0);
                });
            }
        }

        @Test
        void FineConditionTest() {
            for (HardwareType hardwareType : validHardwareTypes) {
                hardwareType.setCondition(Condition.FINE);
                assertThrows(HardwareException.class, () -> {
                    hardwareType.calculateRepairCost(10.0);
                });
            }
        }

        @Nested
        class ComputerRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareException {
                validComputer.setCondition(Condition.VERY_BAD);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(70.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(210.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareException {
                validComputer.setCondition(Condition.BAD);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(50.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(150.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareException {
                validComputer.setCondition(Condition.AVERAGE);
                assertEquals(0.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(20.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(60.0, validComputer.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareException {
                validComputer.setCondition(Condition.DUSTY);
                assertEquals(5.0, validComputer.calculateRepairCost(0.0), 0.001);
                assertEquals(5.0, validComputer.calculateRepairCost(100.0), 0.001);
                assertEquals(5.0, validComputer.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class ConsoleRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareException {
                validConsole.setCondition(Condition.VERY_BAD);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(90.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(270.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareException {
                validConsole.setCondition(Condition.BAD);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareException {
                validConsole.setCondition(Condition.AVERAGE);
                assertEquals(0.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(60.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(180.0, validConsole.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareException {
                validConsole.setCondition(Condition.DUSTY);
                assertEquals(100.0, validConsole.calculateRepairCost(0.0), 0.001);
                assertEquals(100.0, validConsole.calculateRepairCost(100.0), 0.001);
                assertEquals(100.0, validConsole.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class MonitorRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareException {
                validMonitor.setCondition(Condition.VERY_BAD);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(95.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(285.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareException {
                validMonitor.setCondition(Condition.BAD);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(90.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(270.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareException {
                validMonitor.setCondition(Condition.AVERAGE);
                assertEquals(0.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareException {
                validMonitor.setCondition(Condition.DUSTY);
                assertEquals(10.0, validMonitor.calculateRepairCost(0.0), 0.001);
                assertEquals(10.0, validMonitor.calculateRepairCost(100.0), 0.001);
                assertEquals(10.0, validMonitor.calculateRepairCost(300.0), 0.001);
            }
        }

        @Nested
        class PhoneRepairCosts {
            @Test
            void veryBadConditionTests() throws HardwareException {
                validPhone.setCondition(Condition.VERY_BAD);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(80.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(240.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void badConditionTests() throws HardwareException {
                validPhone.setCondition(Condition.BAD);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(50.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(150.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void averageConditionTests() throws HardwareException {
                validPhone.setCondition(Condition.AVERAGE);
                assertEquals(0.0, validPhone.calculateRepairCost(0.0), 0.001);
                assertEquals(20.0, validPhone.calculateRepairCost(100.0), 0.001);
                assertEquals(60.0, validPhone.calculateRepairCost(300.0), 0.001);
            }

            @Test
            void dustyConditionTests() throws HardwareException {
                validPhone.setCondition(Condition.DUSTY);
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
            Computer computer1 = Computer.builder()
                    .id(uuid)
                    .condition(Condition.DUSTY).build();
            Computer computer2 = Computer.builder()
                    .id(uuid)
                    .condition(Condition.FINE).build();

            assertEquals(computer1, computer2);
        }

        @Test
        void computerEqualsNegativeTest() {
            Computer computer1 = Computer.builder()
                    .id(uuid)
                    .condition(Condition.DUSTY).build();
            Computer computer2 = Computer.builder()
                    .id(differentUuid)
                    .condition(Condition.FINE).build();

            assertNotEquals(computer1, computer2);
        }

        @Test
        void consoleEqualsPositiveTest() {
            Console console1 = Console.builder()
                    .id(uuid)
                    .condition(Condition.DUSTY).build();
            Console console2 = Console.builder()
                    .id(uuid)
                    .condition(Condition.FINE).build();

            assertEquals(console1, console2);
        }

        @Test
        void consoleEqualsNegativeTest() {
            Console console1 = Console.builder()
                    .id(uuid)
                    .condition(Condition.DUSTY).build();
            Console console2 = Console.builder()
                    .id(differentUuid)
                    .condition(Condition.FINE).build();

            assertNotEquals(console1, console2);
        }

        @Test
        void monitorEqualsPositiveTest() {
            Monitor monitor1 = Monitor.builder()
                    .id(uuid)
                    .condition(Condition.DUSTY).build();
            Monitor monitor2 = Monitor.builder()
                    .id(uuid)
                    .condition(Condition.FINE).build();

            assertEquals(monitor1, monitor2);
        }

        @Test
        void monitorEqualsNegativeTest() {
            Monitor monitor1 = Monitor.builder()
                    .id(uuid)
                    .condition(Condition.DUSTY).build();
            Monitor monitor2 = Monitor.builder()
                    .id(differentUuid)
                    .condition(Condition.FINE).build();

            assertNotEquals(monitor1, monitor2);
        }

        @Test
        void phoneEqualsPositiveTest() {
            Phone phone1 = Phone.builder()
                    .id(uuid)
                    .condition(Condition.DUSTY).build();
            Phone phone2 = Phone.builder()
                    .id(uuid)
                    .condition(Condition.FINE).build();

            assertEquals(phone1, phone2);
        }

        @Test
        void phoneEqualsNegativeTest() {
            Phone phone1 = Phone.builder()
                    .id(uuid)
                    .condition(Condition.DUSTY).build();
            Phone phone2 = Phone.builder()
                    .id(differentUuid)
                    .condition(Condition.FINE).build();

            assertNotEquals(phone1, phone2);
        }
    }
}