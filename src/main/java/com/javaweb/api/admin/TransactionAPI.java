package com.javaweb.api.admin;

import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transaction;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionAPI {
    @Autowired
    private TransactionService transactionService;
    @PostMapping
    public ResponseEntity<?> createOrUpdateTransaction(@Valid @RequestBody TransactionDTO transaction, BindingResult bindingResult) {
        try{
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errors);
            }
            transactionService.createOrUpdateTransaction(transaction);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage("Transaction Created");
            responseDTO.setData(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
        catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> loadTransaction(@PathVariable Long transactionId) {
        TransactionEntity transactionEntity = transactionService.findById(transactionId);
        ResponseDTO responseDTO = new ResponseDTO();
        if (transactionEntity != null) {
            responseDTO.setData(transactionEntity.getNote());
            responseDTO.setMessage("Transaction Loaded");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }
        else {
            responseDTO.setMessage("Transaction Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }
}
