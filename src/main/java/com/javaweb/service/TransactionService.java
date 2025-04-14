package com.javaweb.service;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> findAllByCodeAndCustomer(String code, CustomerEntity customer);
    void createOrUpdateTransaction(TransactionDTO transactionDTO);
    TransactionEntity findById(Long id);
}
