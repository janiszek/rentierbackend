package com.kjaniszewski.RentierBackend.repository;

import com.kjaniszewski.RentierBackend.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant,Long> {
}
