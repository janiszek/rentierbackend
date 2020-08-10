package com.kjaniszewski.RentierBackend.repository;

import com.kjaniszewski.RentierBackend.entity.TenantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantDetailsRepository extends JpaRepository<TenantDetails,Long> {
}
