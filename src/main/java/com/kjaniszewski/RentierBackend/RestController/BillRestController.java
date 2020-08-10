package com.kjaniszewski.RentierBackend.RestController;

import com.kjaniszewski.RentierBackend.entity.Bill;
import com.kjaniszewski.RentierBackend.repository.BillRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//To set the 'Access-Control-Allow-Origin' in the header
@CrossOrigin
@RestController
@RequestMapping("/bills")
@AllArgsConstructor
public class BillRestController {
    private final BillRepository billRepo;

    // http://localhost:8080/bills
    @GetMapping
    public List<Bill> billFindAll() {
        List<Bill> billList = billRepo.findAll();
        return billList;
    }

    @GetMapping("/{billId}")
    public Bill FindByBillId(@PathVariable(name = "billId") Long billId) {
        return billRepo.findById(billId).orElse(null);
    }

    // http://localhost:8080/bills/filter/1
    @GetMapping("/filter/{locationId}")
    public List<Bill> findAllByLocationId(@PathVariable(name = "locationId") Long locId) {
        List<Bill> billList = billRepo.findAllByLocationIdOrderByDateAscBillGroupAsc(locId);
        return billList;
    }

    // http://localhost:8080/bills/filter/1/1
    @GetMapping("/filter/{locationId}/{groupId}")
    public List<Bill> findAllByLocationIdAndBillGroupId(@PathVariable(name = "locationId") Long locId,
                                                      @PathVariable(name = "groupId") Long groupId) {
        List<Bill> billList = billRepo.findAllByLocationIdAndBillGroupIdOrderByDateAscBillGroupAsc(locId, groupId);
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
        else
            //TODO how to inform about the failed operation?
            return null;
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{billId}")
    public Bill UpdateBill(@PathVariable(name = "billId") Long billId, @RequestBody @Valid Bill bill){
        Optional<Bill> bill1 = billRepo.findById((bill.getId()));
        if (bill1.isPresent()==true) {
            return billRepo.save(bill);
        }
        else
            //TODO how to inform about the failed operation?
            return null;
    }

    //delete a record
    @DeleteMapping("/{billId}")
    public void DeleteBill(@PathVariable(name = "billId") Long billId) {
        billRepo.deleteById(billId);
        //TODO how to inform about the successful operation?
    }

}
