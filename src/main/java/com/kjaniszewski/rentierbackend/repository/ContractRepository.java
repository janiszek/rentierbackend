package com.kjaniszewski.rentierbackend.repository;

import com.kjaniszewski.rentierbackend.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract,Long> {
    List<Contract> findByLocationIdAndDateToAfterOrderByDateToDesc(Long locationId, Date currentDate);
    Page<Contract> findAllByLocationIdAndTenantIdOrderByDateToAsc(Long locId, Long tenantId, Pageable pageable);
    Page<Contract> findAllByLocationIdOrderByDateToAsc(Long locId, Pageable pageable);
    Page<Contract> findAllByTenantIdOrderByDateToAsc(Long tenantId, Pageable pageable);
}
