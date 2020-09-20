package com.kjaniszewski.rentierbackend.restcontroller;

import com.kjaniszewski.rentierbackend.entity.Payment;
import com.kjaniszewski.rentierbackend.repository.PaymentRepository;
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
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentRestController {

    private final PaymentRepository paymentRepo;

    //use the pagination by default
    @GetMapping
    public Page<Payment> FindAll(Pageable pageable) {
        return paymentRepo.findAll(pageable);
    }


    @GetMapping("/{paymentId}")
    public Payment FindByPaymentId(@PathVariable(name = "paymentId") Long paymentId) {
        return paymentRepo.findById(paymentId)
                //.orElse(null);
                .orElseThrow(() ->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cannot find. Payment id: " + paymentId + " does not exist!"));

    }


    //not recommended, use @RequestParam instead
    /*@GetMapping("/filter/{locationId}/{tenantId}")
    public Page<Payment> findAllByLocationIdAndTenantId(@PathVariable(name = "locationId") Long locId,
                                                     @PathVariable(name = "tenantId") Long tenantId, Pageable pageable) {
        Page<Payment> paymentList = paymentRepo.findAllByLocationIdAndTenantIdOrderByDateAsc(locId, tenantId, pageable);
        return paymentList;
    }*/

    @GetMapping("/filterParam")
    public Page<Payment> FilterAllByLocationIdAndTenantId(@RequestParam(name = "locationId", required = false, defaultValue = "0") Long locId,
                                                          @RequestParam(name = "tenantId", required = false, defaultValue = "0") Long tenantId, Pageable pageable)
                                                            throws Exception {
        //value 0 for a param means we want 'any'
        if ((locId == 0) && (tenantId == 0))
            return paymentRepo.findAll(pageable);
        Page<Payment> paymentList = paymentRepo.findAllByLocationIdAndTenantIdOrderByDateAsc(locId, tenantId, pageable);
        return paymentList;
    }

    //add a record
    @ResponseBody
    @PostMapping()
    public Payment InsertPayment(@RequestBody @Valid Payment payment) {
        Optional<Payment> payment1 = paymentRepo.findById(payment.getId());
        if (payment1.isPresent()!=true) {
            return paymentRepo.save(payment);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot insert. Payment id: " + payment.getId() + " already exists!");
        }
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{paymentId}")
    public Payment UpdatePayment(@PathVariable(name = "paymentId") Long paymentId, @RequestBody @Valid Payment payment){
        Optional<Payment> pay1 = paymentRepo.findById(/*payment.getId()*/paymentId);
        if (pay1.isPresent()==true) {
            return paymentRepo.save(payment);
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot update. Payment id: " + paymentId + " does not exist!");
        }
    }

    //delete a record
    @DeleteMapping("/{paymentId}")
    public void DeletePayment(@PathVariable(name = "paymentId") Long paymentId) {
        try {
            paymentRepo.deleteById(paymentId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot delete. Payment id: "+ paymentId +" does not exist!", ex);
        }

    }

}
