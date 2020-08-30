package com.kjaniszewski.rentierbackend.RestController;

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

    /*@GetMapping
    public List<Tenant> FindAll() {
        List<Tenant> tenantList = tenantRepo.findAll();
        return tenantList;
    }*/
    //use the pagination by default
    @GetMapping
    public Page<Tenant> FindAll(Pageable pageable) {
        return tenantRepo.findAll(pageable);
    }


    @GetMapping("/{tenantId}")
    public Tenant FindByTenantId(@PathVariable(name = "tenantId") Long tenId) {
        return tenantRepo.findById(tenId).orElse(null);
    }

    //add a record
    @ResponseBody
    @PostMapping("")
    public Tenant InsertTenant(@RequestBody @Valid Tenant tenant) {
        Optional<Tenant> tenInsert = tenantRepo.findById((tenant.getId()));
        if (tenInsert.isPresent()!=true) {
            return tenantRepo.save(tenant);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot insert. Tenant id " + tenant.getId() + " already exists!", new Exception());
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
                    HttpStatus.BAD_REQUEST, "Cannot update. Tenant id " + tenantId + " does not exist!", new Exception());
        }
    }

    //delete a record
    @DeleteMapping("/{tenantId}")
    public void DeleteTenant(@PathVariable Long tenantId) {
        try {
            tenantRepo.deleteById(tenantId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot delete. Tenant id "+ tenantId +" does not exist!", ex);
        }
    }

}
