package com.kjaniszewski.rentierbackend.RestController;

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

    /*@GetMapping
    public List<Payment> findAll() {
        List<Payment> paymentList = paymentRepo.findAll();
        return paymentList;
    }*/
    //use the pagination by default
    @GetMapping
    public Page<Payment> FindAll(Pageable pageable) {
        return paymentRepo.findAll(pageable);
    }


    @GetMapping("/{paymentId}")
    public Payment FindByPaymentId(@PathVariable(name = "paymentId") Long paymentId) {
        return paymentRepo.findById(paymentId).orElse(null);
    }


    // http://localhost:8080/payments/filter/1/1
    @GetMapping("/filter/{locationId}/{tenantId}")
    public Page<Payment> findAllByLocationIdAndTenantId(@PathVariable(name = "locationId") Long locId,
                                                     @PathVariable(name = "tenantId") Long tenantId, Pageable pageable) {
        Page<Payment> paymentList = paymentRepo.findAllByLocationIdAndTenantIdOrderByDateAsc(locId, tenantId, pageable);
        return paymentList;
    }

    //add a record
    @ResponseBody
    @PostMapping()
    public Payment InsertPayment(@RequestBody @Valid Payment payment) {
        Optional<Payment> payment1 = paymentRepo.findById((payment.getId()));
        if (payment1.isPresent()!=true) {
            return paymentRepo.save(payment);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot insert. Payment id " + payment.getId() + " already exists!", new Exception());
        }
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{paymentId}")
    public Payment UpdatePayment(@PathVariable(name = "paymentId") Long paymentId, @RequestBody @Valid Payment payment){
        Optional<Payment> pay1 = paymentRepo.findById((payment.getId()));
        if (pay1.isPresent()==true) {
            return paymentRepo.save(payment);
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot update. Payment id " + paymentId + " does not exist!", new Exception());
        }
    }

    //delete a record
    @DeleteMapping("/{paymentId}")
    public void DeletePayment(@PathVariable(name = "paymentId") Long paymentId) {
        try {
            paymentRepo.deleteById(paymentId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot delete. Payment id "+ paymentId +" does not exist!", ex);
        }

    }

}
