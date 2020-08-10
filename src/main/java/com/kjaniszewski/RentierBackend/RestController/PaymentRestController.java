package com.kjaniszewski.RentierBackend.RestController;

import com.kjaniszewski.RentierBackend.entity.Payment;
import com.kjaniszewski.RentierBackend.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//To set the 'Access-Control-Allow-Origin' in the header
@CrossOrigin
@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentRestController {

    private final PaymentRepository paymentRepo;

    @GetMapping
    public List<Payment> findAll() {
        List<Payment> paymentList = paymentRepo.findAll();
        return paymentList;
    }

    @GetMapping("/{paymentId}")
    public Payment FindByPaymentId(@PathVariable(name = "paymentId") Long paymentId) {
        return paymentRepo.findById(paymentId).orElse(null);
    }


    // http://localhost:8080/payments/filter/1/1
    @GetMapping("/filter/{locationId}/{tenantId}")
    public List<Payment> findAllByLocationIdAndTenantId(@PathVariable(name = "locationId") Long locId,
                                                     @PathVariable(name = "tenantId") Long tenantId) {
        List<Payment> paymentList = paymentRepo.findAllByLocationIdAndTenantIdOrderByDateAsc(locId, tenantId);
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
        else
            //TODO how to inform about the failed operation?
            return null;
    }

    //edit a record
    @ResponseBody
    @PutMapping("/{paymentId}")
    public Payment UpdatePayment(@PathVariable(name = "paymentId") Long paymentId, @RequestBody @Valid Payment payment){
        Optional<Payment> pay1 = paymentRepo.findById((payment.getId()));
        if (pay1.isPresent()==true) {
            return paymentRepo.save(payment);
        }
        else
            //TODO how to inform about the failed operation?
            return null;
    }

    //delete a record
    @DeleteMapping("/{paymentId}")
    public void DeletePayment(@PathVariable(name = "paymentId") Long paymentId) {
        paymentRepo.deleteById(paymentId);
        //TODO how to inform about the successful operation?
    }

}
