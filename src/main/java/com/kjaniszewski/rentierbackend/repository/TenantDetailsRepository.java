package com.kjaniszewski.rentierbackend.repository;

import com.kjaniszewski.rentierbackend.entity.TenantDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantDetailsRepository extends JpaRepository<TenantDetails,Long> {
}
