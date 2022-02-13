package com.ndourcodeur.laptopservice.controller;

import com.ndourcodeur.laptopservice.dto.LaptopRequest;
import com.ndourcodeur.laptopservice.entity.Laptop;
import com.ndourcodeur.laptopservice.message.Message;
import com.ndourcodeur.laptopservice.repository.LapTopRepository;
import com.ndourcodeur.laptopservice.services.LapTopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/lapTops")
@Slf4j
public class LapTopController {

    @Autowired
    private LapTopRepository lapTopRepository;

    private final LapTopService lapTopService;

    public LapTopController(LapTopService lapTopService) {
        this.lapTopService = lapTopService;
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/add
     */
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewLapTop(@Valid @RequestBody LaptopRequest request){
        log.info("Inside addNewLapTop of LapTopController");
        return new ResponseEntity<>(lapTopService.addLapTop(request), HttpStatus.CREATED);
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/{id}
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateLapTopById(@PathVariable Long id ,@Valid @RequestBody LaptopRequest request){
        log.info("Inside updateLapTopById of LapTopController");
        return new ResponseEntity<>(lapTopService.editLapTop(id, request), HttpStatus.CREATED);
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/all
     */
    @GetMapping(path = "/all")
    public ResponseEntity<?> fetchAllLapTops(){
        log.info("Inside fetchAllLapTops of LapTopController");
        List<Laptop> laptops = lapTopService.findAllLapTops();
        if (laptops.isEmpty())
            return new ResponseEntity<>(new Message("Sorry, No Content Here!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(laptops, HttpStatus.OK);
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/{id}
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> fetchLapTopById(@PathVariable Long id){
        log.info("Inside fetchLapTopById of LapTopController");
        return new ResponseEntity<>(lapTopService.findLapTop(id), HttpStatus.OK);
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/{id}
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteLapTopById(@PathVariable Long id){
        log.info("Inside deleteLapTopById of LapTopController");
        lapTopService.deleteLapTop(id);
        return new ResponseEntity<>(new Message("LapTop deleted successfully with ID:"+id), HttpStatus.OK);
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/byUserId/{userId}
     */
    @GetMapping(path = "/byUserId/{userId}")
    public ResponseEntity<?> fetchAllLapTopsByUserId(@PathVariable Long userId){
        log.info("Inside fetchAllLapTopsByUserId of LapTopController");
        if (!lapTopRepository.existsById(userId))
            return new ResponseEntity<>(new Message("User does not exist with ID:"+userId), HttpStatus.BAD_REQUEST);
        List<Laptop> laptops = lapTopService.findLapTopsByUserId(userId);
        return new ResponseEntity<>(laptops, HttpStatus.OK);
    }

    /**
     *    Fetching LapTop And User Detail (From User Microservice) By LapTop id
     *
     *    URL ===> http://localhost:8300/api/v1/lapTops/detail-laptop-and-user/{lapTopId}
     */
    @GetMapping(path = "/detail-laptop-and-user/{laptopId}")
    public ResponseEntity<?> getDetailLapTopWithUser(@PathVariable Long laptopId){
        log.info("Inside getDetailLapTopWithUser of LapTopController");
        return new ResponseEntity<>(lapTopService.findLapTopWithUser(laptopId), HttpStatus.OK);
    }
}
