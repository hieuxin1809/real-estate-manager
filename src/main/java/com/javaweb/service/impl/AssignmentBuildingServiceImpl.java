package com.javaweb.service.impl;

import com.javaweb.entity.AssignmentBuildingEntity;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.repository.AssignmentBuildingRepository;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.AssignmentBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AssignmentBuildingServiceImpl implements AssignmentBuildingService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private AssignmentBuildingRepository assignmentBuildingRepository;
    @Override
    public void updateAssingmentBuilding(AssignmentBuildingDTO assignmentBuildingDTO) {
        Long buildingId = assignmentBuildingDTO.getBuildingId();
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId).get();
        assignmentBuildingRepository.deleteByBuildingEntity(buildingEntity);
        List<Long> staffIds = assignmentBuildingDTO.getStaffIds();
        List<AssignmentBuildingEntity> assignmentBuildingEntities = new ArrayList<>();
        for (Long staffId : staffIds) {
            UserEntity userEntity = userRepository.findById(staffId).get();
            AssignmentBuildingEntity assignmentBuildingEntity = new AssignmentBuildingEntity();
            assignmentBuildingEntity.setBuildingEntity(buildingEntity);
            assignmentBuildingEntity.setUserEntity(userEntity);
            assignmentBuildingEntities.add(assignmentBuildingEntity);
        }
        assignmentBuildingRepository.saveAll(assignmentBuildingEntities);
    }
}
