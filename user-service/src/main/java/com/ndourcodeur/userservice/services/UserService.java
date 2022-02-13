package com.ndourcodeur.userservice.services;

import com.ndourcodeur.userservice.dto.UserRequest;
import com.ndourcodeur.userservice.entity.User;
import com.ndourcodeur.userservice.payload.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    public User addUser(UserRequest request);
    public User editUser(Long id, UserRequest request);
    public List<User> findAllUsers();
    public User findUser(Long id);
    public User findUserByEmail(String userEmail);
    public void deleteUser(Long id);
    public User getUsername(String username);
    public User getEmail(String email);
    public Boolean existsByUsername(String username);
    public Boolean existsByEmail(String email);
    // Rest Template
    public List<Car> findAllCarsWithUser(Long userId);
    public List<LapTop> findAllLapTopsWithUser(Long userId);
    public Map<String, Object> getUserWithLapTopsAndCars(Long userId);

}
