package com.kjaniszewski.rentierbackend.repository;

import com.kjaniszewski.rentierbackend.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*@Repository*/
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findAllByLocationIdOrderByDateAsc(Long locationId);
    List<Payment> findAllByLocationIdAndTenantIdOrderByDateAsc(Long locationId, Long tenantId);
    Page<Payment> findAllByLocationIdAndTenantIdOrderByDateAsc(Long locationId, Long tenantId, Pageable pageable);
}
