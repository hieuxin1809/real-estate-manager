package com.javaweb.api.admin;

import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.AssignmentBuildingService;
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
    @PostMapping
    public ResponseEntity<?> updateAssingmentBuilding(@RequestBody AssignmentBuildingDTO assignmentBuildingDTO){
        assignmentBuildingService.updateAssingmentBuilding(assignmentBuildingDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("success");
        return ResponseEntity.ok().body(responseDTO);
    }
}
