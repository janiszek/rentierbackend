package com.kjaniszewski.rentierbackend.repository;

import com.kjaniszewski.rentierbackend.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,Long> {
    Page<Bill> findAllByLocationIdOrderByDateAscBillGroupAsc(Long locationId, Pageable pageable);
    Page<Bill> findAllByLocationIdAndBillGroupIdOrderByDateAscBillGroupAsc(Long locationId, Long groupId, Pageable pageable);
}
