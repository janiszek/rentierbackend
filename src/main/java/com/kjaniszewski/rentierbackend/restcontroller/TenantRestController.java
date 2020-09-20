package com.kjaniszewski.rentierbackend.restcontroller;

import com.kjaniszewski.rentierbackend.entity.Tenant;
import com.kjaniszewski.rentierbackend.repository.TenantRepository;
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
@RequestMapping("/tenants")
@AllArgsConstructor
public class TenantRestController {

    private final TenantRepository tenantRepo;

    //use the pagination by default
    @GetMapping
    public Page<Tenant> FindAll(Pageable pageable) {
        return tenantRepo.findAll(pageable);
    }

    @GetMapping("/{tenantId}")
    public Tenant FindByTenantId(@PathVariable(name = "tenantId") Long tenId) {
        return tenantRepo.findById(tenId)
                //.orElse(null);
                .orElseThrow(() ->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cannot find. Tenant id: " + tenId + " does not exist!"));

    }

    //add a record
    @ResponseBody
    @PostMapping("")
    public Tenant InsertTenant(@RequestBody @Valid Tenant tenant) {
        Optional<Tenant> tenInsert = tenantRepo.findById(tenant.getId());
        if (tenInsert.isPresent()!=true) {
            return tenantRepo.save(tenant);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot insert. Tenant id " + tenant.getId() + " already exists!");
        }
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{tenantId}")
    public Tenant UpdateTenant(@PathVariable(name = "tenantId") Long tenantId, @RequestBody @Valid Tenant tenant){
        Optional<Tenant> tenUpdate = tenantRepo.findById(/*tenant.getId()*/ tenantId);
        if (tenUpdate.isPresent()==true) {
            return tenantRepo.save(tenant);
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot update. Tenant id: " + tenantId + " does not exist!");
        }
    }

    //delete a record
    @DeleteMapping("/{tenantId}")
    public void DeleteTenant(@PathVariable Long tenantId) {
        try {
            tenantRepo.deleteById(tenantId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot delete. Tenant id: "+ tenantId +" does not exist!", ex);
        }
    }

}
