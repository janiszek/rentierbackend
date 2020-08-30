package com.kjaniszewski.rentierbackend.RestController;

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

    /*@GetMapping
    public List<Contract> FindAll() {
        List<Contract> contractList = contractRepo.findAll();
        return contractList;
    }*/
    //use the pagination by default
    @GetMapping
    public Page<Contract> FindAll(Pageable pageable) {
        return contractRepo.findAll(pageable);
    }

    @GetMapping("/{contractId}")
    public Contract FindByTenantId(@PathVariable(name = "contractId") Long conId) {
        return contractRepo.findById(conId).orElse(null);
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

    /*@GetMapping("/filter/{locationId}/{tenantId}")
    public List<Contract> FindAllByLocationIdAndTenantId(@PathVariable(name = "locationId") Long locId,
                                                        @PathVariable(name = "tenantId") Long tenantId) {
        List<Contract> contractList;
        if ((locId !=0) && (tenantId !=0)) {
            contractList = contractRepo.findAllByLocationIdAndTenantIdOrderByDateToAsc(locId, tenantId);
        }
        else if (locId !=0){
            contractList = contractRepo.findAllByLocationIdOrderByDateToAsc(locId);
        } else {
            contractList = contractRepo.findAllByTenantIdOrderByDateToAsc(tenantId);
        }
        return contractList;
    }*/

    @GetMapping("/filter/{locationId}/{tenantId}")
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
    }

    //add a record
    @ResponseBody
    @PostMapping()
    public Contract InsertContract(@RequestBody @Valid Contract contract) {
        Optional<Contract> cont1 = contractRepo.findById((contract.getId()));
        if (cont1.isPresent()!=true) {
            return contractRepo.save(contract);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot insert. Contract id " + contract.getId() + " already exists!", new Exception());
        }
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{contractId}")
    public Contract UpdateContract(@PathVariable(name = "contractId") Long contractId, @RequestBody @Valid Contract contract){
        Optional<Contract> loc1 = contractRepo.findById((contract.getId()));
        if (loc1.isPresent()==true) {
            return contractRepo.save(contract);
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot update. Contract id " + contractId + " does not exist!", new Exception());
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
                    HttpStatus.BAD_REQUEST, "Cannot delete. Contract id "+ contractId +" does not exist!", ex);
        }
    }

}
