package com.kjaniszewski.RentierBackend.repository;

import com.kjaniszewski.RentierBackend.entity.BillGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillGroupRepository extends JpaRepository<BillGroup,Long> {
}
