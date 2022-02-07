package com.ndourcodeur.userservice.services;

import com.ndourcodeur.userservice.dto.UserRequest;
import com.ndourcodeur.userservice.entity.User;
import com.ndourcodeur.userservice.exception.ResourceNotFoundException;
import com.ndourcodeur.userservice.payload.*;
import com.ndourcodeur.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    public RestTemplate restTemplate;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UserRequest request) {
        User user = new User();
        user.setId(request.getId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        log.info("Inside addUser of UserService");
        return userRepository.save(user);
    }

    @Override
    public User editUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+id));
        user.setId(request.getId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        log.info("Inside editUser of UserService");
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Inside findAllUsers of UserService");
        return userRepository.findAll();
    }

    @Override
    public User findUser(Long id) {
        log.info("Inside findUser of UserService");
        return userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+id));
    }

   /* @Override
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }*/

    @Override
    public List<Car> findAllCarsWithUser(Long userId) {
        List<Car> cars = restTemplate.getForObject("http://localhost:8200/api/v1/cars/byUserId/" + userId, List.class);
        return cars;
    }

    @Override
    public List<LapTop> findAllLapTopsWithUser(Long userId) {
        List<LapTop> lapTops = restTemplate.getForObject("http://localhost:8300/api/v1/lapTops/byUserId/" + userId, List.class);
        return lapTops;
    }

    @Override
    public ResponseTemplateUserWithCarAndLapTop findUserWithCarAndLapTop(Long userId) {
        ResponseTemplateUserWithCarAndLapTop response = new ResponseTemplateUserWithCarAndLapTop();
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+userId));
        Car car = restTemplate.getForObject("http://localhost:8200/api/v1/cars/" + userId, Car.class);
        LapTop lapTop = restTemplate.getForObject("http://localhost:8300/api/v1/lapTops/" + user.getId(), LapTop.class);
        response.setUser(user);
        response.setCar(car);
        response.setLapTop(lapTop);
        return response;
    }

    @Override
    public ResponseTemplateUserWithCar findUserWithCar(Long userId) {
        ResponseTemplateUserWithCar response = new ResponseTemplateUserWithCar();
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+userId));
        Car car = restTemplate.getForObject("http://localhost:8200/api/v1/cars/" + userId, Car.class);
        response.setUser(user);
        response.setCar(car);
        return response;
    }

    @Override
    public ResponseTemplateUserWithLapTop findUserWithLapTop(Long userId) {
        ResponseTemplateUserWithLapTop response = new ResponseTemplateUserWithLapTop();
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+userId));
        LapTop lapTop = restTemplate.getForObject("http://localhost:8300/api/v1/lapTops/" + user.getId(), LapTop.class);
        response.setUser(user);
        response.setLapTop(lapTop);
        return response;
    }

   /* @Override
    public ResponseTemplatePayload findAllUsersWithCarAndLapTop() {
        return null;
    }*/

   /* @Override
    public ResponseTemplatePayload findUserWithCarAndLapTop(Long userId) {
        ResponseTemplatePayload response = new ResponseTemplatePayload();
        //User user = userRepository.findById(idUser)
        User user = userRepository.findByUserId(userId);
                //.orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+userId));
        Car car = restTemplate.getForObject("http://localhost:8200/api/v1/cars/" + user.getId(), Car.class);
        LapTop lapTop = restTemplate.getForObject("http://localhost:8300/api/v1/lapTops/" + user.getId(), LapTop.class);
        response.setUser(user);
        response.setCar(car);
        response.setLapTop(lapTop);
        return response;
    }*/

    @Override
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+id));
        log.info("Inside deleteUser of UserService");
        userRepository.delete(existingUser);
    }

    @Override
    public User getUsername(String username) {
        log.info("Inside getUsername of UserService");
        return userRepository.findByUsernameContaining(username);
    }

    @Override
    public User getEmail(String email) {
        log.info("Inside getEmail of UserService");
        return userRepository.findByEmailContaining(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
