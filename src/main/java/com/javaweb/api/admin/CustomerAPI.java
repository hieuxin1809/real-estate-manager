package com.javaweb.api.admin;


import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.service.CustomerService;
import com.javaweb.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/customer")
public class CustomerAPI {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @GetMapping
    private Object GetCustomer(@RequestParam CustomerSearchRequest customerSearchRequest) {
        List<CustomerSearchResponse> customers = customerService.findAll(customerSearchRequest);
        return customers;
    }
    @PostMapping
    public ResponseEntity<?> createOrUpdateCustomer(@Valid @RequestBody CustomerDTO newCustomerDTO, BindingResult bindingResult) {
        try{
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
            customerService.createOrUpdateCustomer(newCustomerDTO);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage("Customer created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }
    @DeleteMapping("/{ids}")
    private ResponseEntity<?> DeleteCustomers(@PathVariable List<Long> ids) {
        if(ids.size() == 0){
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage("No find ids");
            return ResponseEntity.badRequest().body(responseDTO);
        }
        else{
            ResponseDTO responseDTO = new ResponseDTO();
            customerService.deleteCustomerByIds(ids);
            responseDTO.setMessage("Delete Customers Success");
            return ResponseEntity.ok().body(responseDTO);
        }
    }
    @GetMapping("/{customerId}/staffs")
    public ResponseEntity<?> loadStaffCustomer(@PathVariable Long customerId) {
        List<StaffResponseDTO> staffs = userService.LoadStaffCustomer(customerId);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(staffs);
        responseDTO.setMessage("Staff List");
        return ResponseEntity.ok().body(responseDTO);
    }
}
