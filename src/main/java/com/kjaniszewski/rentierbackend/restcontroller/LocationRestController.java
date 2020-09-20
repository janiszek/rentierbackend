package com.kjaniszewski.rentierbackend.restcontroller;

import com.kjaniszewski.rentierbackend.entity.Location;
import com.kjaniszewski.rentierbackend.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

//To set the 'Access-Control-Allow-Origin' in the header
@CrossOrigin
@RestController
@RequestMapping("/locations")
@AllArgsConstructor
public class LocationRestController {
    private final LocationRepository locationRepo;

    //use the pagination by default
    @GetMapping
    public Page<Location> FindAll(Pageable pageable) {
        return locationRepo.findAll(pageable);
    }


    @GetMapping("/{locationId}")
    public Location FindByLocationId(@PathVariable(name = "locationId") Long locId) {
        return locationRepo.findById(locId)
                //.orElse(null);
                .orElseThrow(() ->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cannot find. Location id: " + locId + " does not exist!"));

    }

    //add a record
    @ResponseBody
    @PostMapping()
    public Location InsertLocation(@RequestBody @Valid Location location) {
        Optional<Location> loc1 = locationRepo.findById(location.getId());
        if (loc1.isPresent()!=true) {
            return locationRepo.save(location);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot insert. Location id: " + location.getId() + " already exists!");
        }
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{locId}")
    public Location UpdateLocation(@PathVariable(name = "locId") Long locId, @RequestBody @Valid Location location){
        Optional<Location> loc1 = locationRepo.findById(/*location.getId()*/locId);
        if (loc1.isPresent()==true) {
            return locationRepo.save(location);
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot update. Location id: " + locId + " does not exist!");
        }
    }

    //delete a record
    @DeleteMapping("/{locId}")
    public void DeleteLocation(@PathVariable Long locId) {
        try {
            locationRepo.deleteById(locId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot delete. Location id: "+ locId +" does not exist!", ex);
        }
    }

}
