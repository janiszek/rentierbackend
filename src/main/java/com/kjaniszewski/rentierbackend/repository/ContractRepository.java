package com.kjaniszewski.rentierbackend.repository;

import com.kjaniszewski.rentierbackend.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

/*@Repository*/
public interface ContractRepository extends JpaRepository<Contract,Long> {
    List<Contract> findByLocationIdAndDateToAfterOrderByDateToDesc(Long locationId, Date currentDate);
    List<Contract> findAllByLocationIdAndTenantIdOrderByDateToAsc(Long locId, Long tenantId);
    Page<Contract> findAllByLocationIdAndTenantIdOrderByDateToAsc(Long locId, Long tenantId, Pageable pageable);
    List<Contract> findAllByLocationIdOrderByDateToAsc(Long locId);
    Page<Contract> findAllByLocationIdOrderByDateToAsc(Long locId, Pageable pageable);
    List<Contract> findAllByTenantIdOrderByDateToAsc(Long tenantId);
    Page<Contract> findAllByTenantIdOrderByDateToAsc(Long tenantId, Pageable pageable);
}
