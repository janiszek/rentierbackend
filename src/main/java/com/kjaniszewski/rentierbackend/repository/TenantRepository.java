package com.kjaniszewski.rentierbackend.repository;

import com.kjaniszewski.rentierbackend.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant,Long> {
}
