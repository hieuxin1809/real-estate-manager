package com.javaweb.controller.admin;



import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.enums.District;
import com.javaweb.enums.TypeCode;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.service.BuildingService;
import com.javaweb.service.impl.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@RestController(value="buildingControllerOfAdmin")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UserService userService;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("/admin/building-list")
    public ModelAndView getBuildings(@ModelAttribute("modelSearch") BuildingSearchRequest params) {
        ModelAndView mav = new ModelAndView("admin/building/list");
        // xuong service => repo de lay data theo yeu cau cua phia client => project 2
        List<BuildingSearchResponse> results = buildingService.findAll(params);
        mav.addObject("buildingList", results);
        mav.addObject("staffs" , userService.getListStaff());
        mav.addObject("districts" , District.getdistrict());
        mav.addObject("typeCode" , TypeCode.getType());
        return mav;
    }
    @GetMapping("/admin/building-edit")
    public ModelAndView addBuilding(@ModelAttribute("building") BuildingDTO dto) {

        ModelAndView mav = new ModelAndView("admin/building/edit");
        mav.addObject("districts" , District.getdistrict());
        mav.addObject("typeCode" , TypeCode.getType());
        return mav;
    }
    @GetMapping("/admin/building-edit-{id}")
    public ModelAndView editBuilding(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("admin/building/edit");
        mav.addObject("districts" , District.getdistrict());
        mav.addObject("typeCode" , TypeCode.getType());
        // find building by id ... => buildingEntity => BuildingDTO
        BuildingDTO buildingDTO = buildingService.editBuilding(id);
        mav.addObject("building", buildingDTO);
        return mav;
    }

}
