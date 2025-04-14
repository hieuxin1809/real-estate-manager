package com.javaweb.service.impl;

import com.javaweb.entity.AssignmentCustomerEntity;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.repository.AssignmentCustomerRepository;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.AssignmentCustomerService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AssignmentCustomerServiceImpl implements AssignmentCustomerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AssignmentCustomerRepository assignmentCustomerRepository;
    @Override
    public void updateAssignCustomer(AssignmentCustomerDTO assignmentCustomerDTO) {
        Long customerId = assignmentCustomerDTO.getCustomerId();
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        assignmentCustomerRepository.deleteByCustomer(customerEntity);
        // danh sach staff dc chon
        List<Long> staffs = assignmentCustomerDTO.getStaffIds();
        if (staffs == null || staffs.isEmpty()) {
            throw new RuntimeException("Staff list cannot be null or empty");
        }
        List<AssignmentCustomerEntity> assignmentCustomerEntities = new ArrayList<>();
        for(Long staffId : staffs){
            UserEntity userEntity = userRepository.findById(staffId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + staffId));
            AssignmentCustomerEntity assignmentCustomerEntity = new AssignmentCustomerEntity();
            assignmentCustomerEntity.setCustomer(customerEntity);
            assignmentCustomerEntity.setUserEntity(userEntity);
            assignmentCustomerEntities.add(assignmentCustomerEntity);
        }
        assignmentCustomerRepository.saveAll(assignmentCustomerEntities);
    }
}
