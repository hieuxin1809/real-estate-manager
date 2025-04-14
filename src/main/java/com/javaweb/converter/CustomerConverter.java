package com.javaweb.converter;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.enums.Status;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerRepository customerRepository;
    public CustomerSearchResponse toCustomerSearchResponse(CustomerEntity it){
        CustomerSearchResponse customerSearchResponse = modelMapper.map(it, CustomerSearchResponse.class);
        return customerSearchResponse;
    }
    public CustomerEntity toCustomerEntity(CustomerDTO customerDTO){
        CustomerEntity customerEntity = modelMapper.map(customerDTO, CustomerEntity.class);
        if(customerEntity.getStatus() == null){
            customerEntity.setStatus(Status.CHUA_XU_LY.getStatusName());
        }
        else{
            customerEntity.setStatus(Status.getStatusNameByKey(customerDTO.getStatus()));
        }
        if(customerDTO.getId() != null){
            CustomerEntity existCustomer = customerRepository.findById(customerDTO.getId()).get();
            customerEntity.setAssignmentCustomers(existCustomer.getAssignmentCustomers());
            customerEntity.setTransactions(existCustomer.getTransactions());
            customerEntity.setCreatedBy(existCustomer.getCreatedBy());
            customerEntity.setCreatedDate(existCustomer.getCreatedDate());
        }
        return customerEntity;
    }
    public CustomerDTO toCustomerDTO(CustomerEntity customerEntity){
        CustomerDTO customerDTO = modelMapper.map(customerEntity, CustomerDTO.class);
        customerDTO.setStatus(Status.getKeyByStatusName(customerEntity.getStatus()));
        return customerDTO;
    }
}
