package com.javaweb.api.admin;

import com.javaweb.entity.AssignmentCustomerEntity;
import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.AssignmentBuildingService;
import com.javaweb.service.AssignmentCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assingments/")
public class AssingmentAPI {

    @Autowired
    private AssignmentBuildingService assignmentBuildingService;
    @Autowired
    private AssignmentCustomerService assignmentCustomerService;
    @PostMapping("/building")
    public ResponseEntity<?> updateAssingmentBuilding(@RequestBody AssignmentBuildingDTO assignmentBuildingDTO){
        assignmentBuildingService.updateAssingmentBuilding(assignmentBuildingDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("success");
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("/customer")
    public ResponseEntity<?> updateAssingmentCustomer(@RequestBody AssignmentCustomerDTO assignmentCustomerDTO){
        assignmentCustomerService.updateAssignCustomer(assignmentCustomerDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("success");
        return ResponseEntity.ok().body(responseDTO);
    }
}
