package com.kjaniszewski.RentierBackend.repository;

import com.kjaniszewski.RentierBackend.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long> {
    List<Contract> findByLocationIdAndDateToAfterOrderByDateToDesc(Long locationId, Date currentDate);
    List<Contract> findAllByLocationIdAndTenantIdOrderByDateToAsc(Long locId, Long tenantId);
    List<Contract> findAllByLocationIdOrderByDateToAsc(Long locId);
    List<Contract> findAllByTenantIdOrderByDateToAsc(Long tenantId);

}
