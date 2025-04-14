package com.javaweb.service;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerSearchResponse> findAll(CustomerSearchRequest request);
    void deleteCustomerByIds(List<Long> ids);
    CustomerEntity createOrUpdateCustomer(CustomerDTO customer);
    CustomerEntity findByIdAndIsActive(Long id, Integer isActive);
    boolean isStaffOfCustomer(Long id, Long customerId);
}
