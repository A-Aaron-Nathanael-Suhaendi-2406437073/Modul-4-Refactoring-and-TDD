package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {
    private Car car;

    @BeforeEach
    void setUp() {
        this.car = new Car();
        this.car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.car.setCarName("Pajero Sport");
        this.car.setCarColor("Hitam");
        this.car.setCarQuantity(10);
    }

    @Test
    void testGetCarId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Pajero Sport", this.car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Hitam", this.car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(10, this.car.getCarQuantity());
    }
}