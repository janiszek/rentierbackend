package com.kjaniszewski.RentierBackend.repository;

import com.kjaniszewski.RentierBackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findAllByLocationIdOrderByDateAsc(Long locationId);
    List<Payment> findAllByLocationIdAndTenantIdOrderByDateAsc(Long locationId, Long tenantId);
}
