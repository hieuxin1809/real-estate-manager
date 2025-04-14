package com.javaweb.repository;

import com.javaweb.entity.AssignmentCustomerEntity;
import com.javaweb.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentCustomerRepository extends JpaRepository<AssignmentCustomerEntity, Long> {
    void deleteByCustomer(CustomerEntity customerEntity);
}
