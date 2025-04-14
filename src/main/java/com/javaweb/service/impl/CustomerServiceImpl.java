package com.javaweb.service.impl;

import com.javaweb.converter.CustomerConverter;
import com.javaweb.entity.AssignmentCustomerEntity;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerConverter customerConverter;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<CustomerSearchResponse> findAll(CustomerSearchRequest request) {
        List<CustomerEntity> customerEntities = customerRepository.findAll(request);
        List<CustomerSearchResponse> customerSearchResponses = new ArrayList<>();
        for (CustomerEntity customerEntity : customerEntities) {
            CustomerSearchResponse customerSearchResponse = customerConverter.toCustomerSearchResponse(customerEntity);
            customerSearchResponses.add(customerSearchResponse);
        }
        return customerSearchResponses;
    }

    @Override
    public void deleteCustomerByIds(List<Long> ids) {
        List<CustomerEntity> customerEntities = customerRepository.findAllById(ids);
        for (CustomerEntity customerEntity : customerEntities) {
            customerEntity.setIsActive(0);
        }
        customerRepository.saveAll(customerEntities);
    }

    @Override
    public CustomerEntity createOrUpdateCustomer(CustomerDTO customer) {
        CustomerEntity customerEntity = customerConverter.toCustomerEntity(customer);
        return customerRepository.save(customerEntity);
    }
    @Override
    public CustomerEntity findByIdAndIsActive(Long id, Integer isActive) {
        return customerRepository.findByIdAndIsActive(id,isActive);
    }

    @Override
    public boolean isStaffOfCustomer(Long id, Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).get();
        UserEntity user = userRepository.findById(id).get();
        System.out.println("Assignment customers: " + customerEntity.getAssignmentCustomers().size());
        List<UserEntity> userEntities = customerEntity.getAssignmentCustomers().stream()
                .map(AssignmentCustomerEntity::getUserEntity)
                .collect(Collectors.toList());
        return userEntities.contains(user);
    }
}
