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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        log.info("Inside addUser of UserService");
        User user = new User();
        user.setId(request.getId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User editUser(Long id, UserRequest request) {
        log.info("Inside editUser of UserService");
        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+id));
        user.setId(request.getId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
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

    @Override
    public User findUserByEmail(String email) {
        log.info("Inside findUserByEmail of UserService");
        return userRepository.findByEmailContaining(email);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Inside findAllLapTopsWithUser of UserService");
        User existingUser = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+id));
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
        log.info("Inside existsByUsername of UserService");
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        log.info("Inside existsByEmail of UserService");
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<Car> findAllCarsWithUser(Long userId) {
        log.info("Inside findAllCarsWithUser of UserService");
        List<Car> cars = restTemplate.getForObject("http://car-service/api/v1/cars/byUserId/" + userId, List.class);
        return cars;
    }

    @Override
    public List<LapTop> findAllLapTopsWithUser(Long userId) {
        log.info("Inside deleteUser of UserService");
        List<LapTop> lapTops = restTemplate.getForObject("http://laptop-service/api/v1/lapTops/byUserId/" + userId, List.class);
        return lapTops;
    }

    @Override
    public Map<String, Object> getUserWithLapTopsAndCars(Long userId) {
        log.info("Inside getUserWithLapTopsAndCars of UserService");
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if (user==null) {
            response.put("message", "User does not exits with ID:" + userId);
            return response;
        }
        response.put("User", user);
        // Fetching All Cars
        List<Car> cars = findAllCarsWithUser(userId);
        if (cars.isEmpty())
            response.put("Cars", "Sorry, No Content!");
        else
            response.put("Cars", cars);
        // Fetching All LapTops
        List<LapTop> lapTops = findAllLapTopsWithUser(userId);
        if (lapTops.isEmpty())
            response.put("LapTops", "Sorry, No Content!");
        else
            response.put("LapTops", lapTops);
        return response;
    }

}
