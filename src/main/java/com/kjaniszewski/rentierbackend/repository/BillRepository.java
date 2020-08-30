package com.kjaniszewski.rentierbackend.repository;

import com.kjaniszewski.rentierbackend.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*@Repository*/
public interface BillRepository extends JpaRepository<Bill,Long> {
    List<Bill> findAllByLocationIdOrderByDateAscBillGroupAsc(Long locationId);
    Page<Bill> findAllByLocationIdOrderByDateAscBillGroupAsc(Long locationId, Pageable pageable);
    List<Bill> findAllByLocationIdAndBillGroupIdOrderByDateAscBillGroupAsc(Long locationId, Long groupId);
    Page<Bill> findAllByLocationIdAndBillGroupIdOrderByDateAscBillGroupAsc(Long locationId, Long groupId, Pageable pageable);
}
