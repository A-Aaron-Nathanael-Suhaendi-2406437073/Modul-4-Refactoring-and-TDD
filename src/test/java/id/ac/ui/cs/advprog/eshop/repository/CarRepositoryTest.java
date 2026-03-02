package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarId("test-id-1");
        car.setCarName("Honda Civic");
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
    }

    @Test
    void testFindById() {
        Car car = new Car();
        car.setCarId("test-id-2");
        car.setCarName("Toyota Yaris");
        carRepository.create(car);

        Car foundCar = carRepository.findById("test-id-2");
        assertNotNull(foundCar);
        assertEquals("Toyota Yaris", foundCar.getCarName());
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarId("test-id-3");
        car.setCarName("Suzuki Swift");
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Suzuki Swift Sport");
        updatedCar.setCarColor("Kuning");
        updatedCar.setCarQuantity(5);

        carRepository.update("test-id-3", updatedCar);

        Car checkedCar = carRepository.findById("test-id-3");
        assertEquals("Suzuki Swift Sport", checkedCar.getCarName());
        assertEquals("Kuning", checkedCar.getCarColor());
        assertEquals(5, checkedCar.getCarQuantity());
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("test-id-4");
        car.setCarName("Mazda 3");
        carRepository.create(car);

        carRepository.delete("test-id-4");

        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }
}