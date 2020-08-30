package com.kjaniszewski.rentierbackend.RestController;

import com.kjaniszewski.rentierbackend.entity.Bill;
import com.kjaniszewski.rentierbackend.repository.BillRepository;
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
@RequestMapping("/bills")
@AllArgsConstructor
public class BillRestController {
    private final BillRepository billRepo;

    // http://localhost:8080/bills
    /*@GetMapping
    public List<Bill> billFindAll() {
        List<Bill> billList = billRepo.findAll();
        return billList;
    }*/
    //use the pagination by default
    @GetMapping
    public Page<Bill> FindAll(Pageable pageable) {
        return billRepo.findAll(pageable);
    }


    @GetMapping("/{billId}")
    public Bill FindByBillId(@PathVariable(name = "billId") Long billId) {
        return billRepo.findById(billId).orElse(null);
    }

    // http://localhost:8080/bills/filter/1
    @GetMapping("/filter/{locationId}")
    public Page<Bill> findAllByLocationId(@PathVariable(name = "locationId") Long locId, Pageable pageable) {
        Page<Bill> billList = billRepo.findAllByLocationIdOrderByDateAscBillGroupAsc(locId, pageable);
        return billList;
    }

    // http://localhost:8080/bills/filter/1/1
    @GetMapping("/filter/{locationId}/{groupId}")
    public Page<Bill> findAllByLocationIdAndBillGroupId(@PathVariable(name = "locationId") Long locId,
                                                      @PathVariable(name = "groupId") Long groupId, Pageable pageable) {
        Page<Bill> billList = billRepo.findAllByLocationIdAndBillGroupIdOrderByDateAscBillGroupAsc(locId, groupId, pageable);
        return billList;
    }

    //add a record
    @ResponseBody
    @PostMapping()
    public Bill InsertBill(@RequestBody @Valid Bill bill) {
        Optional<Bill> bill1 = billRepo.findById((bill.getId()));
        if (bill1.isPresent()!=true) {
            return billRepo.save(bill);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot insert. Bill id " + bill.getId() + " already exists!", new Exception());
        }
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{billId}")
    public Bill UpdateBill(@PathVariable(name = "billId") Long billId, @RequestBody @Valid Bill bill){
        Optional<Bill> bill1 = billRepo.findById((bill.getId()));
        if (bill1.isPresent()==true) {
            return billRepo.save(bill);
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot update. Bill id " + billId + " does not exist!", new Exception());
        }
    }

    //delete a record
    @DeleteMapping("/{billId}")
    public void DeleteBill(@PathVariable(name = "billId") Long billId) {
        try {
            billRepo.deleteById(billId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot delete. Bill id "+ billId +" does not exist!", ex);
        }
    }

}
