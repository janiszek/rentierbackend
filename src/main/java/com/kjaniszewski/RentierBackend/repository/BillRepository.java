package com.kjaniszewski.RentierBackend.repository;

import com.kjaniszewski.RentierBackend.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
    List<Bill> findAllByLocationIdOrderByDateAscBillGroupAsc(Long locationId);
    List<Bill> findAllByLocationIdAndBillGroupIdOrderByDateAscBillGroupAsc(Long locationId, Long groupId);
}
