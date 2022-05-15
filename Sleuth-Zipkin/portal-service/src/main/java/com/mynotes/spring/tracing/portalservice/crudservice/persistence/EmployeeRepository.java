package com.mynotes.spring.tracing.portalservice.crudservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    public EmployeeEntity findByFname(String fname);


}
