package com.kjaniszewski.rentierbackend.repository;

import com.kjaniszewski.rentierbackend.entity.BillGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillGroupRepository extends JpaRepository<BillGroup,Long> {
}
