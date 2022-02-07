package com.ndourcodeur.carservice.services;

import com.ndourcodeur.carservice.dto.CarRequest;
import com.ndourcodeur.carservice.entity.Car;
import com.ndourcodeur.carservice.exception.ResourceNotFoundException;
import com.ndourcodeur.carservice.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car addCar(CarRequest request) {
        Car car = new Car();
        car.setId(request.getId());
        car.setName(request.getName());
        car.setPrice(request.getPrice());
        car.setBrand(request.getBrand());
        car.setRegistrationNumber(UUID.randomUUID().toString());
        car.setIsInStock(request.getIsInStock());
        car.setUserId(request.getUserId());
        return carRepository.save(car);
    }

    @Override
    public Car editCar(Long id, CarRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Car does not exist with ID:"+id));
        car.setId(request.getId());
        car.setName(request.getName());
        car.setPrice(request.getPrice());
        car.setBrand(request.getBrand());
        car.setRegistrationNumber(UUID.randomUUID().toString());
        car.setIsInStock(request.getIsInStock());
        car.setUserId(request.getUserId());
        return carRepository.save(car);
    }

    @Override
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car findCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Car does not exist with ID:"+id));
    }

    @Override
    public List<Car> findByUserId(Long userId) {
        return carRepository.findByUserId(userId);
    }

    @Override
    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Car does not exist with ID:"+id));
        carRepository.delete(car);
    }

   /* @Override
    public Car getName(String name) {
        return carRepository.findByNameContaining(name);
    }

    @Override
    public Boolean existsByName(String name) {
        return carRepository.existsByName(name);
    }*/
}
