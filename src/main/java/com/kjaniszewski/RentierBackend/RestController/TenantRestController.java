package com.kjaniszewski.RentierBackend.RestController;

import com.kjaniszewski.RentierBackend.entity.Tenant;
import com.kjaniszewski.RentierBackend.repository.TenantRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//To set the 'Access-Control-Allow-Origin' in the header
@CrossOrigin
@RestController
@RequestMapping("/tenants")
@AllArgsConstructor
public class TenantRestController {

    private final TenantRepository tenantRepo;

    @GetMapping
    public List<Tenant> FindAll() {
        List<Tenant> tenantList = tenantRepo.findAll();
        return tenantList;
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
        else
            //error - trying to add existing
            return null;
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{tenantId}")
    public Tenant UpdateTenant(@PathVariable(name = "tenantId") Long tenantId, @RequestBody @Valid Tenant tenant){
        Optional<Tenant> tenUpdate = tenantRepo.findById(/*tenant.getId()*/ tenantId);
        if (tenUpdate.isPresent()==true) {
            return tenantRepo.save(tenant);
        }
        else
            //error - trying to save non-existing
            return null;
    }

    //delete a record
    @DeleteMapping("/{tenantid}")
    public void DeleteTenant(@PathVariable Long tenId) {
        tenantRepo.deleteById(tenId);
        //TODO how to inform about the successful operation?
    }

}
