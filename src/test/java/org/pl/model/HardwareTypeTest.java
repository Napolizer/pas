package org.pl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.HardwareException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.pl.model.Condition.*;

class HardwareTypeTest {

    @Nested
    @DisplayName("Computer Test")
    class ComputerTest {
        Computer computer;

        @BeforeEach
        void setUp() {
            computer = new Computer(BAD);
        }

        @Test
        void getCondition() {
            assertEquals(BAD, computer.getCondition());
        }

        @Test
        void setCondition() {
            computer.setCondition(UNREPAIRABLE);

            assertEquals(UNREPAIRABLE, computer.getCondition());
        }

        @Test
        void testEquals() {
            assertNotEquals(null, computer);
            assertNotEquals(2, computer);
            assertNotEquals("123", computer);
            assertEquals(computer, computer);

            List<HardwareType> hardwareTypeList = List.of(
                    new Monitor(FINE), new Console(DUSTY), new Phone(UNREPAIRABLE)
            );

            for (HardwareType hardwareType : hardwareTypeList) {
                assertNotEquals(hardwareType, computer);
            }

            assertEquals(new Computer(computer.getCondition()), computer);
        }

        @Test
        void testToString() {
            assertEquals(String.format("Computer(condition=%s)", computer.getCondition()), computer.toString());
        }

        @Nested
        @DisplayName("Calculate Repair Cost Test")
        class CalculateRepairCostTest {

            @Test
            void priceBelowZero() {
                for (int price = -100; price < 0; price += 49) {
                    final int finalPrice = price;
                    HardwareException exception = assertThrows(HardwareException.class, () -> computer.calculateRepairCost(finalPrice));
                    assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION, exception.getMessage());
                }
            }

            @Test
            void conditionUnrepairable() {
                computer.setCondition(UNREPAIRABLE);
                HardwareException exception = assertThrows(HardwareException.class, () -> computer.calculateRepairCost(100));
                assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION, exception.getMessage());
            }

            @Test
            void conditionAccepted() throws HardwareException {
                computer.setCondition(VERY_BAD);
                assertEquals(70.0, computer.calculateRepairCost(100));
                computer.setCondition(BAD);
                assertEquals(50.0, computer.calculateRepairCost(100));
                computer.setCondition(AVERAGE);
                assertEquals(20.0, computer.calculateRepairCost(100));

                computer.setCondition(DUSTY);
                assertEquals(5, computer.calculateRepairCost(1000));
                assertEquals(5, computer.calculateRepairCost(100));
                assertEquals(5, computer.calculateRepairCost(1));
            }

            @Test
            void conditionFine() {
                computer.setCondition(FINE);
                HardwareException exception = assertThrows(HardwareException.class, () -> computer.calculateRepairCost(100));
                assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION, exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Monitor Test")
    class MonitorTest {
        Monitor monitor;

        @BeforeEach
        void setUp() {
            monitor = new Monitor(FINE);
        }

        @Test
        void getCondition() {
            assertEquals(FINE, monitor.getCondition());
        }

        @Test
        void setCondition() {
            monitor.setCondition(UNREPAIRABLE);

            assertEquals(UNREPAIRABLE, monitor.getCondition());
        }

        @Test
        void testEquals() {
            assertNotEquals(null, monitor);
            assertNotEquals(2, monitor);
            assertNotEquals("123", monitor);
            assertEquals(monitor, monitor);

            List<HardwareType> hardwareTypeList = List.of(
                    new Computer(FINE), new Console(DUSTY), new Phone(UNREPAIRABLE)
            );

            for (HardwareType hardwareType : hardwareTypeList) {
                assertNotEquals(hardwareType, monitor);
            }

            assertEquals(new Monitor(monitor.getCondition()), monitor);
        }

        @Test
        void testToString() {
            assertEquals(String.format("Monitor(condition=%s)", monitor.getCondition()), monitor.toString());
        }

        @Nested
        @DisplayName("Calculate Repair Cost Test")
        class CalculateRepairCostTest {

            @Test
            void priceBelowZero() {
                for (int price = -100; price < 0; price += 49) {
                    final int finalPrice = price;
                    HardwareException exception = assertThrows(HardwareException.class, () -> monitor.calculateRepairCost(finalPrice));
                    assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION, exception.getMessage());
                }
            }

            @Test
            void conditionUnrepairable() {
                monitor.setCondition(UNREPAIRABLE);
                HardwareException exception = assertThrows(HardwareException.class, () -> monitor.calculateRepairCost(100));
                assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION, exception.getMessage());
            }

            @Test
            void conditionAccepted() throws HardwareException {
                monitor.setCondition(VERY_BAD);
                assertEquals(95.0, monitor.calculateRepairCost(100));
                monitor.setCondition(BAD);
                assertEquals(90.0, monitor.calculateRepairCost(100));
                monitor.setCondition(AVERAGE);
                assertEquals(80.0, monitor.calculateRepairCost(100));

                monitor.setCondition(DUSTY);
                assertEquals(10, monitor.calculateRepairCost(1000));
                assertEquals(10, monitor.calculateRepairCost(100));
                assertEquals(10, monitor.calculateRepairCost(1));
            }

            @Test
            void conditionFine() {
                monitor.setCondition(FINE);
                HardwareException exception = assertThrows(HardwareException.class, () -> monitor.calculateRepairCost(100));
                assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION, exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Console Test")
    class ConsoleTest {
        Console console;

        @BeforeEach
        void setUp() {
            console = new Console(DUSTY);
        }

        @Test
        void getCondition() {
            assertEquals(DUSTY, console.getCondition());
        }

        @Test
        void setCondition() {
            console.setCondition(UNREPAIRABLE);

            assertEquals(UNREPAIRABLE, console.getCondition());
        }

        @Test
        void testEquals() {
            assertNotEquals(null, console);
            assertNotEquals(2, console);
            assertNotEquals("123", console);
            assertEquals(console, console);

            List<HardwareType> hardwareTypeList = List.of(
                    new Monitor(FINE), new Computer(DUSTY), new Phone(UNREPAIRABLE)
            );

            for (HardwareType hardwareType : hardwareTypeList) {
                assertNotEquals(hardwareType, console);
            }

            assertEquals(new Console(console.getCondition()), console);
        }

        @Test
        void testToString() {
            assertEquals(String.format("Console(condition=%s)", console.getCondition()), console.toString());
        }

        @Nested
        @DisplayName("Calculate Repair Cost Test")
        class CalculateRepairCostTest {

            @Test
            void priceBelowZero() {
                for (int price = -100; price < 0; price += 49) {
                    final int finalPrice = price;
                    HardwareException exception = assertThrows(HardwareException.class, () -> console.calculateRepairCost(finalPrice));
                    assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION, exception.getMessage());
                }
            }

            @Test
            void conditionUnrepairable() {
                console.setCondition(UNREPAIRABLE);
                HardwareException exception = assertThrows(HardwareException.class, () -> console.calculateRepairCost(100));
                assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION, exception.getMessage());
            }

            @Test
            void conditionAccepted() throws HardwareException {
                console.setCondition(VERY_BAD);
                assertEquals(90.0, console.calculateRepairCost(100));
                console.setCondition(BAD);
                assertEquals(80.0, console.calculateRepairCost(100));
                console.setCondition(AVERAGE);
                assertEquals(60.0, console.calculateRepairCost(100));

                console.setCondition(DUSTY);
                assertEquals(100, console.calculateRepairCost(1000));
                assertEquals(100, console.calculateRepairCost(100));
                assertEquals(100, console.calculateRepairCost(1));
            }

            @Test
            void conditionFine() {
                console.setCondition(FINE);
                HardwareException exception = assertThrows(HardwareException.class, () -> console.calculateRepairCost(100));
                assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION, exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Phone Test")
    class PhoneTest {
        Phone phone;

        @BeforeEach
        void setUp() {
            phone = new Phone(UNREPAIRABLE);
        }

        @Test
        void getCondition() {
            assertEquals(UNREPAIRABLE, phone.getCondition());
        }

        @Test
        void setCondition() {
            phone.setCondition(FINE);

            assertEquals(FINE, phone.getCondition());
        }

        @Test
        void testEquals() {
            assertNotEquals(null, phone);
            assertNotEquals(2, phone);
            assertNotEquals("123", phone);
            assertEquals(phone, phone);

            List<HardwareType> hardwareTypeList = List.of(
                    new Computer(FINE), new Console(DUSTY), new Monitor(UNREPAIRABLE)
            );

            for (HardwareType hardwareType : hardwareTypeList) {
                assertNotEquals(hardwareType, phone);
            }

            assertEquals(new Phone(phone.getCondition()), phone);
        }

        @Test
        void testToString() {
            assertEquals(String.format("Phone(condition=%s)", phone.getCondition()), phone.toString());
        }

        @Nested
        @DisplayName("Calculate Repair Cost Test")
        class CalculateRepairCostTest {

            @Test
            void priceBelowZero() {
                for (int price = -100; price < 0; price += 49) {
                    final int finalPrice = price;
                    HardwareException exception = assertThrows(HardwareException.class, () -> phone.calculateRepairCost(finalPrice));
                    assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION, exception.getMessage());
                }
            }

            @Test
            void conditionUnrepairable() {
                phone.setCondition(UNREPAIRABLE);
                HardwareException exception = assertThrows(HardwareException.class, () -> phone.calculateRepairCost(100));
                assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION, exception.getMessage());
            }

            @Test
            void conditionAccepted() throws HardwareException {
                phone.setCondition(VERY_BAD);
                assertEquals(80.0, phone.calculateRepairCost(100));
                phone.setCondition(BAD);
                assertEquals(50.0, phone.calculateRepairCost(100));
                phone.setCondition(AVERAGE);
                assertEquals(20.0, phone.calculateRepairCost(100));

                phone.setCondition(DUSTY);
                assertEquals(5, phone.calculateRepairCost(1000));
                assertEquals(5, phone.calculateRepairCost(100));
                assertEquals(5, phone.calculateRepairCost(1));
            }

            @Test
            void conditionFine() {
                phone.setCondition(FINE);
                HardwareException exception = assertThrows(HardwareException.class, () -> phone.calculateRepairCost(100));
                assertEquals(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION, exception.getMessage());
            }
        }
    }
}