package com.kjaniszewski.RentierBackend.RestController;

import com.kjaniszewski.RentierBackend.entity.Location;
import com.kjaniszewski.RentierBackend.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//To set the 'Access-Control-Allow-Origin' in the header
@CrossOrigin
@RestController
@RequestMapping("/locations")
@AllArgsConstructor
public class LocationRestController {
    private final LocationRepository locationRepo;

    @GetMapping
    public List<Location> FindAll() {
        List<Location> locationList = locationRepo.findAll();
        return locationList;
    }

    @GetMapping("/{locationId}")
    public Location FindByLocationId(@PathVariable(name = "locationId") Long locId) {
        return locationRepo.findById(locId).orElse(null);
    }


    //add a record
    @ResponseBody
    @PostMapping()
    public Location InsertLocation(@RequestBody @Valid Location location) {
        Optional<Location> loc1 = locationRepo.findById((location.getId()));
        if (loc1.isPresent()!=true) {
            return locationRepo.save(location);
        }
        else
            //TODO how to inform about the failed operation?
            return null;
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{locId}")
    public Location UpdateLocation(@PathVariable(name = "locId") Long locId, @RequestBody @Valid Location location){
        Optional<Location> loc1 = locationRepo.findById((location.getId()));
        if (loc1.isPresent()==true) {
            return locationRepo.save(location);
        }
        else
            //TODO how to inform about the failed operation?
            return null;
    }

    //delete a record
    @DeleteMapping("/{locid}")
    public void DeleteLocation(@PathVariable Long locid) {
        locationRepo.deleteById(locid);
        //TODO how to inform about the successful operation?
    }

}
