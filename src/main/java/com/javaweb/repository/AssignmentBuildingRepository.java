package com.javaweb.repository;

import com.javaweb.entity.AssignmentBuildingEntity;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentBuildingRepository extends JpaRepository<AssignmentBuildingEntity , Long> {
    void deleteByBuildingEntity(BuildingEntity buildingEntity);
    void deleteByBuildingEntity_IdIn(List<Long> id);
}
