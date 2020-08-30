package com.kjaniszewski.rentierbackend.repository;

import com.kjaniszewski.rentierbackend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/*@Repository*/
public interface LocationRepository extends JpaRepository<Location,Long> {
}
