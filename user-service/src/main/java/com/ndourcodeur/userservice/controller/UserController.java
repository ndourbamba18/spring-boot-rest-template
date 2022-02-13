package com.ndourcodeur.userservice.controller;

import com.ndourcodeur.userservice.dto.UserRequest;
import com.ndourcodeur.userservice.entity.User;
import com.ndourcodeur.userservice.message.Message;
import com.ndourcodeur.userservice.payload.Car;
import com.ndourcodeur.userservice.payload.LapTop;
import com.ndourcodeur.userservice.repository.UserRepository;
import com.ndourcodeur.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *    URL ===> http://localhost:8100/api/v1/users/add
     */
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody UserRequest request){
        if (userService.existsByUsername(request.getUsername()))
            return new ResponseEntity<>(new Message("Username already exist!"), HttpStatus.BAD_REQUEST);
        if (userService.existsByEmail(request.getEmail()))
            return new ResponseEntity<>(new Message("Email already exist!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.addUser(request), HttpStatus.CREATED);
    }

    /**
     *    URL ===> http://localhost:8100/api/v1/users/{idUser}
     */
    @PutMapping(path = "/{idUser}")
    public ResponseEntity<?> updateUserById(@PathVariable Long idUser ,@Valid @RequestBody UserRequest request){
        if (userService.existsByUsername(request.getUsername()) && userService.getUsername(request.getUsername()).getId() != idUser)
            return new ResponseEntity<>(new Message("Username already exist!"), HttpStatus.BAD_REQUEST);
        if (userService.existsByEmail(request.getEmail()) && userService.getEmail(request.getEmail()).getId() != idUser)
            return new ResponseEntity<>(new Message("Email already exist!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.editUser(idUser, request), HttpStatus.CREATED);
    }

    /**
     *    URL ===> http://localhost:8100/api/v1/users/all
     */
    @GetMapping(path = "/all")
    public ResponseEntity<?> fetchAllUsers(){
        List<User> users = userService.findAllUsers();
        if (users.isEmpty())
            return new ResponseEntity<>(new Message("Sorry, No Content Here!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     *    URL ===> http://localhost:8100/api/v1/users/user-detail/{idUser}
     */
    @GetMapping(path = "/user-detail/{idUser}")
    public ResponseEntity<?> fetchUserById(@PathVariable Long idUser){
        return new ResponseEntity<>(userService.findUser(idUser), HttpStatus.OK);
    }

    /**
     *    URL ===> http://localhost:8100/api/v1/users/user/detail-email/{userEmail}
     */
    @GetMapping(path = "/user/detail-email/{email}")
    public ResponseEntity<?> fetchUserByEmail(@PathVariable String email){
        if (!userService.existsByEmail(email))
            return new ResponseEntity<>(new Message("User does not exist with userEmail:"+email), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
    }

    /**
     *    URL ===> http://localhost:8100/api/v1/users/{idUser}
     */
    @DeleteMapping(path = "/{idUser}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long idUser){
        userService.deleteUser(idUser);
        return new ResponseEntity<>(new Message("User deleted successfully with ID:"+idUser), HttpStatus.OK);
    }

    // Rest Template

    /**
     *    URL ===> http://localhost:8100/api/v1/users/cars/{userId}
     */
    @GetMapping(path = "/cars/{userId}")
    public ResponseEntity<?> fetchAllCarsWithUser(@PathVariable Long userId){
        if (!userRepository.existsById(userId))
            return new ResponseEntity<>(new Message("User does not exist with ID:"+userId), HttpStatus.BAD_REQUEST);
        List<Car> cars = userService.findAllCarsWithUser(userId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    /**
     *    URL ===> http://localhost:8100/api/v1/users/lapTops/{userId}
     */
    @GetMapping(path = "/lapTops/{userId}")
    public ResponseEntity<?> fetchAllLapTopsWithUser(@PathVariable Long userId){
        if (!userRepository.existsById(userId))
            return new ResponseEntity<>(new Message("User does not exist with ID:"+userId), HttpStatus.BAD_REQUEST);
        List<LapTop> lapTops = userService.findAllLapTopsWithUser(userId);
        return new ResponseEntity<>(lapTops, HttpStatus.OK);
    }

    /**
     *    Fetching All Cars And All LapTops By UserId From Car Microservice And LapTop Microservices Using Rest Template
     *
     *    URL ===> http://localhost:8100/api/v1/users/cars-and-lapTops/{userId}
     */
    @GetMapping(path = "/cars-and-lapTops/{userId}")
    public ResponseEntity<?> getAllCarsAndAllLapTopsByUserId(@PathVariable Long userId){
        Map<String, Object> result = userService.getUserWithLapTopsAndCars(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
