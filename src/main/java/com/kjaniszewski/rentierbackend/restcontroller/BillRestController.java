package com.kjaniszewski.rentierbackend.restcontroller;

import com.kjaniszewski.rentierbackend.dto.BillDTO;
import com.kjaniszewski.rentierbackend.dto.BillMapper;
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
    private final BillMapper billMapper;

    //use the pagination by default
    @GetMapping
    public Page<Bill> FindAll(Pageable pageable) {
        return billRepo.findAll(pageable);
    }

    @GetMapping("/{billId}")
    public Bill FindByBillId(@PathVariable(name = "billId") Long billId) {
        return billRepo.findById(billId)
                //.orElse(null);
                .orElseThrow(() ->new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cannot find. Bill id: " + billId + " does not exist!"));
    }

    @GetMapping("/DTO/{billId}")
    public BillDTO FindDTOByBillId(@PathVariable(name = "billId") Long billId) {
        return billMapper.toBillDTO(billRepo.findById(billId)
                //.orElse(null);
                .orElseThrow(() ->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cannot find. Bill id: " + billId + " does not exist!")));
    }

    // http://localhost:8080/bills/filter/1
    @GetMapping("/filter/{locationId}")
    public Page<Bill> findAllByLocationId(@PathVariable(name = "locationId") Long locId, Pageable pageable) {
        Page<Bill> billList = billRepo.findAllByLocationIdOrderByDateAscBillGroupAsc(locId, pageable);
        return billList;
    }

    //not recommended, use @RequestParam instead
    /*@GetMapping("/filter/{locationId}/{groupId}")
    public Page<Bill> findAllByLocationIdAndBillGroupId(@PathVariable(name = "locationId") Long locId,
                                                      @PathVariable(name = "groupId") Long groupId, Pageable pageable) {
        Page<Bill> billList = billRepo.findAllByLocationIdAndBillGroupIdOrderByDateAscBillGroupAsc(locId, groupId, pageable);
        return billList;
    }*/

    @GetMapping("/filterParam")
    public Page<Bill> FilterAllByLocationIdAndBillGroupId(@RequestParam(name = "locationId", required = false, defaultValue = "0") Long locId,
                                                            @RequestParam(name = "groupId", required = false, defaultValue = "0") Long groupId, Pageable pageable)
                                                            throws Exception {
        //value 0 for a param means we want 'any'
        if ((locId == 0) && (groupId == 0))
            return billRepo.findAll(pageable);
        Page<Bill> billList = billRepo.findAllByLocationIdAndBillGroupIdOrderByDateAscBillGroupAsc(locId, groupId, pageable);
        return billList;
    }


    //add a record
    @ResponseBody
    @PostMapping()
    public Bill InsertBill(@RequestBody @Valid Bill bill) {
        Optional<Bill> bill1 = billRepo.findById(bill.getId());
        if (bill1.isPresent()!=true) {
            return billRepo.save(bill);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot insert. Bill id: " + bill.getId() + " already exists!");
        }
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{billId}")
    public Bill UpdateBill(@PathVariable(name = "billId") Long billId, @RequestBody @Valid Bill bill){
        Optional<Bill> bill1 = billRepo.findById(/*bill.getId()*/billId);
        if (bill1.isPresent()==true) {
            return billRepo.save(bill);
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot update. Bill id: " + billId + " does not exist!");
        }
    }

    //delete a record
    @DeleteMapping("/{billId}")
    public void DeleteBill(@PathVariable(name = "billId") Long billId) {
        try {
            billRepo.deleteById(billId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot delete. Bill id: "+ billId +" does not exist!", ex);
        }
    }

}
