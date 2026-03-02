package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Pajero Sport");
        car.setCarColor("Hitam");
        car.setCarQuantity(10);
    }

    @Test
    void testCreateCar() {
        when(carRepository.create(car)).thenReturn(car);
        Car savedCar = carService.create(car);
        assertEquals(car.getCarId(), savedCar.getCarId());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAllCars() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        Iterator<Car> iterator = carList.iterator();

        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> allCars = carService.findAll();
        assertFalse(allCars.isEmpty());
        assertEquals(1, allCars.size());
        assertEquals(car.getCarId(), allCars.get(0).getCarId());
    }

    @Test
    void testFindById() {
        when(carRepository.findById(car.getCarId())).thenReturn(car);
        Car foundCar = carService.findById(car.getCarId());
        assertNotNull(foundCar);
        assertEquals(car.getCarId(), foundCar.getCarId());
    }

    @Test
    void testUpdateCar() {
        carService.update(car.getCarId(), car);
        verify(carRepository, times(1)).update(car.getCarId(), car);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById(car.getCarId());
        verify(carRepository, times(1)).delete(car.getCarId());
    }
}