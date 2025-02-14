package com.javaweb.service.impl;

import com.javaweb.Builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.entity.AssignmentBuildingEntity;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.enums.District;
import com.javaweb.enums.TypeCode;
import com.javaweb.exception.ServiceException;
import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.repository.AssignmentBuildingRepository;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.service.BuildingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private RentAreaRepository rentAreaRepository;
    @Autowired
    private BuildingConverter buildingConverter;
    @Autowired
    private BuildingSearchBuilderConverter buildingSearchBuilderConverter;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AssignmentBuildingRepository assignmentBuildingRepository;

    @Override
    public List<BuildingSearchResponse> findAll(BuildingSearchRequest request) {
        BuildingSearchBuilder builder = buildingSearchBuilderConverter.toBuildingSearchBuilder(request);
        List<BuildingEntity> buildingEntities = buildingRepository.findAll(builder);
        List<BuildingSearchResponse> results = new ArrayList<>();
        for (BuildingEntity buildingEntity : buildingEntities) {
            BuildingSearchResponse buildingSearchResponse = buildingConverter.toBuildingSearchResponse(buildingEntity);
            results.add(buildingSearchResponse);
        }
        return results;
    }

    @Override
    public void deleteById(List<Long> ids) {
        // xoa cac lien ket voi bang building
        assignmentBuildingRepository.deleteByBuildingEntity_IdIn(ids);
        rentAreaRepository.deleteByBuildingEntity_IdIn(ids);
        buildingRepository.deleteByIdIn(ids);
    }

    @Override
    public BuildingEntity createOrUpdateBuildings(BuildingDTO buildingDTO) throws ServiceException {
        // convert dto sang entity
        BuildingEntity buildingEntity = buildingConverter.toBuildingEntity(buildingDTO);
        if(buildingEntity.getId() != null ) {
            rentAreaRepository.deleteByBuildingEntity(buildingEntity);
        }
        String rentAreas = buildingDTO.getRentArea();
        List<String> tmp = Arrays.stream(rentAreas.split(",")).collect(Collectors.toList());
        List<RentAreaEntity> rentAreaEntityList = new ArrayList<>();
        for(String s : tmp){
            RentAreaEntity rentAreaEntity1 = new RentAreaEntity();
            rentAreaEntity1.setBuildingEntity(buildingEntity);
            try{
                rentAreaEntity1.setValue(Long.parseLong(s));
            }
            catch(ServiceException e){
                throw new ServiceException(e.getMessage());
            }
            rentAreaEntityList.add(rentAreaEntity1);
        }
        buildingRepository.save(buildingEntity);
        rentAreaRepository.saveAll(rentAreaEntityList);
        return null;
    }

    @Override
    public BuildingDTO editBuilding(Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        BuildingDTO buildingDTO = modelMapper.map(buildingEntity, BuildingDTO.class);
        List<String> typeCode = Arrays.stream(buildingEntity.getTypeCode().split(",")).collect(Collectors.toList());
        buildingDTO.setTypeCode(typeCode);
        List<RentAreaEntity> rentAreaEntityList = buildingEntity.getRentAreas();
        String rentArea = rentAreaEntityList.stream().map(i -> i.getValue().toString()).collect(Collectors.joining(","));
        buildingDTO.setRentArea(rentArea);
        return buildingDTO;
    }
}
