package com.ndourcodeur.laptopservice.controller;

import com.ndourcodeur.laptopservice.dto.LaptopRequest;
import com.ndourcodeur.laptopservice.entity.Laptop;
import com.ndourcodeur.laptopservice.message.Message;
import com.ndourcodeur.laptopservice.services.LapTopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/lapTops")
public class LapTopController {

    private final LapTopService lapTopService;

    public LapTopController(LapTopService lapTopService) {
        this.lapTopService = lapTopService;
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/add
     */
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewLapTop(@Valid @RequestBody LaptopRequest request){
        return new ResponseEntity<>(lapTopService.addLapTop(request), HttpStatus.CREATED);
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/{id}
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateLapTopById(@PathVariable Long id ,@Valid @RequestBody LaptopRequest request){
        return new ResponseEntity<>(lapTopService.editLapTop(id, request), HttpStatus.CREATED);
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/all
     */
    @GetMapping(path = "/all")
    public ResponseEntity<?> fetchAllLapTops(){
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
        return new ResponseEntity<>(lapTopService.findLapTop(id), HttpStatus.OK);
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/byUserId/{userId}
     */
    @GetMapping(path = "/byUserId/{userId}")
    public ResponseEntity<?> fetchAllLapTopsByUserId(@PathVariable Long userId){
        List<Laptop> laptops = lapTopService.findByUserId(userId);
        if (laptops.isEmpty())
            return new ResponseEntity<>(new Message("Sorry, No Content Almost!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(laptops, HttpStatus.OK);
    }

    /**
     *    URL ===> http://localhost:8300/api/v1/lapTops/{id}
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteLapTopById(@PathVariable Long id){
        lapTopService.deleteLapTop(id);
        return new ResponseEntity<>(new Message("LapTop deleted successfully with ID:"+id), HttpStatus.OK);
    }
}
