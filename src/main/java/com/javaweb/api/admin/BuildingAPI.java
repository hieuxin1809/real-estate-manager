package com.javaweb.api.admin;


import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.BuildingService;
import com.javaweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
public class BuildingAPI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private IUserService userService;
    @GetMapping
    private Object GetBuildings(@RequestParam BuildingSearchRequest buildingSearchRequest) {
        List<BuildingSearchResponse> buildings = buildingService.findAll(buildingSearchRequest);
        return buildings;
    }
    @PostMapping
    public ResponseEntity<?> createOrUpdateBuildings(@Valid @RequestBody BuildingDTO buildingDTO , BindingResult bindingResult) {
        try{
            if(bindingResult.hasErrors()){
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
            buildingService.createOrUpdateBuildings(buildingDTO);
            ResponseDTO responseDTO = new ResponseDTO();
            if(buildingDTO.getId() == null){
                responseDTO.setMessage("Building is add created");
            }
            else {
                responseDTO.setMessage("Building is updated");
            }
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }
    // Xoa
    @DeleteMapping("/{ids}")
    public ResponseEntity<?> deleteBuildings(@PathVariable List<Long> ids) {
        if(ids.size() == 0){
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage("Ids can't be empty");
            return ResponseEntity.badRequest().body(responseDTO);
        }
        else {
            System.out.println("Delete buildings");
            ResponseDTO responseDTO = new ResponseDTO();
            buildingService.deleteById(ids);
            responseDTO.setMessage("Buildings deleted successfully");
            return ResponseEntity.ok().body(responseDTO);
        }
    }
    // Load Danh Sach Giao
    @GetMapping("/{buildingId}/staffs")
    public ResponseEntity<?> loadStaffBuilding(@PathVariable Long buildingId) {
        // xuong service
        List<StaffResponseDTO> staffResponseDTOS = userService.LoadStaffBuilding(buildingId);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(staffResponseDTOS);
        responseDTO.setMessage("success");
        return ResponseEntity.ok().body(responseDTO);
    }
}
