package com.kjaniszewski.rentierbackend.restcontroller;

import com.kjaniszewski.rentierbackend.entity.Contract;
import com.kjaniszewski.rentierbackend.repository.ContractRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

//To set the 'Access-Control-Allow-Origin' in the header
@CrossOrigin
@RestController
@RequestMapping("/contracts")
@AllArgsConstructor
public class ContractRestController {
    private final ContractRepository contractRepo;

    //use the pagination by default
    @GetMapping
    public Page<Contract> FindAll(Pageable pageable) {
        return contractRepo.findAll(pageable);
    }

    @GetMapping("/{contractId}")
    public Contract FindByTenantId(@PathVariable(name = "contractId") Long conId) {
        return contractRepo.findById(conId)
                //.orElse(null);
                .orElseThrow(() ->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cannot find. Contract id: " + conId + " does not exist!"));

    }

    @GetMapping("/find-current/{locationId}")
    public Contract FindByLocationIdAndDateToAfterOrderByDateToDesc(@PathVariable(name = "locationId") Long locId) {
        List<Contract> contractList = contractRepo.findByLocationIdAndDateToAfterOrderByDateToDesc(locId, new Date(System.currentTimeMillis()));
        if (contractList.size()>0) {
            //get the latest valid contract
            return contractList.get(0);
        }
        else
            return null;
    }

    //not recommended, use @RequestParam instead
    /*@GetMapping("/filter/{locationId}/{tenantId}")
    public Page<Contract> FindAllByLocationIdAndTenantId(@PathVariable(name = "locationId") Long locId,
                                                        @PathVariable(name = "tenantId") Long tenantId, Pageable pageable) {
        Page<Contract> contractList;
        if ((locId !=0) && (tenantId !=0)) {
            contractList = contractRepo.findAllByLocationIdAndTenantIdOrderByDateToAsc(locId, tenantId, pageable);
        }
        else if (locId !=0){
            contractList = contractRepo.findAllByLocationIdOrderByDateToAsc(locId, pageable);
        } else {
            contractList = contractRepo.findAllByTenantIdOrderByDateToAsc(tenantId, pageable);
        }
        return contractList;
    }*/

    @GetMapping("/filterParam")
    public Page<Contract> FilterAllByLocationIdAndTenantId(@RequestParam(name = "locationId", required = false, defaultValue = "0") Long locId,
                                                         @RequestParam(name = "tenantId", required = false, defaultValue = "0") Long tenantId, Pageable pageable)
                                                            throws Exception {
        //value 0 for a param means we want 'any'
        if ((locId == 0) && (tenantId == 0))
            return contractRepo.findAll(pageable);
        Page<Contract> contractList;
        if ((locId !=0) && (tenantId !=0)) {
            contractList = contractRepo.findAllByLocationIdAndTenantIdOrderByDateToAsc(locId, tenantId, pageable);
        }
        else if (locId !=0){
            contractList = contractRepo.findAllByLocationIdOrderByDateToAsc(locId, pageable);
        } else {
            contractList = contractRepo.findAllByTenantIdOrderByDateToAsc(tenantId, pageable);
        }
        return contractList;
    }


    //add a record
    @ResponseBody
    @PostMapping()
    public Contract InsertContract(@RequestBody @Valid Contract contract) {
        Optional<Contract> cont1 = contractRepo.findById(contract.getId());
        if (cont1.isPresent()!=true) {
            return contractRepo.save(contract);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot insert. Contract id: " + contract.getId() + " already exists!");
        }
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{contractId}")
    public Contract UpdateContract(@PathVariable(name = "contractId") Long contractId, @RequestBody @Valid Contract contract){
        Optional<Contract> loc1 = contractRepo.findById(/*contract.getId()*/contractId);
        if (loc1.isPresent()==true) {
            return contractRepo.save(contract);
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot update. Contract id: " + contractId + " does not exist!");
        }
    }

    //delete a record
    @ResponseBody
    @DeleteMapping("/{contractId}")
    public void DeleteContract(@PathVariable(name = "contractId") Long contractId) {
        try {
            contractRepo.deleteById(contractId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot delete. Contract id: "+ contractId +" does not exist!", ex);
        }
    }

}
