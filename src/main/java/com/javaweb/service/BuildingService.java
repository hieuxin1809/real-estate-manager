package com.javaweb.service;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.exception.ServiceException;
import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface BuildingService {
    List<BuildingSearchResponse> findAll(BuildingSearchRequest request);
    void deleteById(List<Long> ids);
    BuildingEntity createOrUpdateBuildings(BuildingDTO buildingDTO) throws ServiceException;
    BuildingDTO editBuilding(Long id);
    boolean isStaffOfBuilding(Long id , Long buildingId);
    List<BuildingDTO> getAllBuildings();
}
