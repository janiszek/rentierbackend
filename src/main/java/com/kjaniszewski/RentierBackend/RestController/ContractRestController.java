package com.kjaniszewski.RentierBackend.RestController;

import com.kjaniszewski.RentierBackend.entity.Contract;
import com.kjaniszewski.RentierBackend.repository.ContractRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<Contract> FindAll() {
        List<Contract> contractList = contractRepo.findAll();
        return contractList;
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

    @GetMapping("/filter/{locationId}/{tenantId}")
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
    }

    //add a record
    @ResponseBody
    @PostMapping()
    public Contract InsertContract(@RequestBody @Valid Contract contract) {
        Optional<Contract> cont1 = contractRepo.findById((contract.getId()));
        if (cont1.isPresent()!=true) {
            return contractRepo.save(contract);
        }
        else
            //TODO how to inform about the failed operation?
            return null;
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{contractId}")
    public Contract UpdateContract(@PathVariable(name = "contractId") Long contractId, @RequestBody @Valid Contract contract){
        Optional<Contract> loc1 = contractRepo.findById((contract.getId()));
        if (loc1.isPresent()==true) {
            return contractRepo.save(contract);
        }
        else
            //TODO how to inform about the failed operation?
            return null;
    }

    //delete a record
    @ResponseBody
    @DeleteMapping("/{contractId}")
    public void DeleteContract(@PathVariable(name = "contractId") Long contractId) {
        contractRepo.deleteById(contractId);
        //TODO how to inform about the successful operation?
    }

}
