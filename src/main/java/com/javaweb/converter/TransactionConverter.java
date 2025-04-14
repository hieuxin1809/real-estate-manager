package com.javaweb.converter;

import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TransactionRepository transactionRepository;
    public TransactionDTO toTransactionDTO(TransactionEntity transactionEntity) {
        return modelMapper.map(transactionEntity, TransactionDTO.class);
    }
    public TransactionEntity toTransactionEntity(TransactionDTO transactionDTO) {
        TransactionEntity transactionEntity = modelMapper.map(transactionDTO, TransactionEntity.class);
        if(transactionDTO.getId() != null) {
            TransactionEntity existTransaction = transactionRepository.findById(transactionDTO.getId()).get();
            transactionEntity.setCreatedBy(existTransaction.getCreatedBy());
            transactionEntity.setCreatedDate(existTransaction.getCreatedDate());
        }
        return transactionEntity;
    }
}
