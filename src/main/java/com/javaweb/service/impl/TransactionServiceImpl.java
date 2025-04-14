package com.javaweb.service.impl;

import com.javaweb.converter.TransactionConverter;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.TransactionRepository;
import com.javaweb.service.CustomerService;
import com.javaweb.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionConverter transactionConverter;
    @Override
    public List<TransactionDTO> findAllByCodeAndCustomer(String code, CustomerEntity customer) {
        List<TransactionEntity> transactionEntities = transactionRepository.findAllByCodeAndCustomer(code, customer);
         List<TransactionDTO> transactionDTOS = new ArrayList<>();
         for (TransactionEntity transactionEntity : transactionEntities) {
             TransactionDTO transactionDTO = transactionConverter.toTransactionDTO(transactionEntity);
             transactionDTOS.add(transactionDTO);
         }
         return transactionDTOS;
    }

    @Override
    public void createOrUpdateTransaction(TransactionDTO transactionDTO) {
        TransactionEntity transactionEntity = transactionConverter.toTransactionEntity(transactionDTO);
        CustomerEntity customerEntity = customerRepository.findById(transactionDTO.getCustomerId()).get();
        transactionEntity.setCustomer(customerEntity);
        transactionRepository.save(transactionEntity);
    }

    @Override
    public TransactionEntity findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }
}
