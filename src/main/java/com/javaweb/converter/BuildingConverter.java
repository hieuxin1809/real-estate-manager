package com.javaweb.converter;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.enums.District;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.repository.BuildingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BuildingConverter {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BuildingRepository buildingRepository;

    public BuildingSearchResponse toBuildingSearchResponse(BuildingEntity it) {
        BuildingSearchResponse bdDto = modelMapper.map(it, BuildingSearchResponse.class);
        String districtName = "";
        if(it.getDistrict() != null && it.getDistrict() != "") {
            districtName = District.getdistrict().get(it.getDistrict());
        }
        bdDto.setAddress(it.getStreet() + " " + it.getWard() + " " + districtName);
        List<RentAreaEntity> rentAreaEntities = it.getRentAreas();
        // java 8
        String rentArea = rentAreaEntities.stream().map(x-> x.getValue().toString()).collect(Collectors.joining(","));
        bdDto.setRentArea(rentArea);
        bdDto.setImage(it.getImage());
        return bdDto;
    }
    public BuildingEntity toBuildingEntity(BuildingDTO bdDto) {
        BuildingEntity buildingEntity = modelMapper.map(bdDto, BuildingEntity.class);
        String result = bdDto.getTypeCode().stream().collect(Collectors.joining(","));
        buildingEntity.setTypeCode(result);
        if(bdDto.getId() != null) {
            BuildingEntity building = buildingRepository.findById(bdDto.getId()).get();
            buildingEntity.setImage(building.getImage());
        }
        return buildingEntity;
    }
    public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
        List<RentAreaEntity> rentAreas = buildingEntity.getRentAreas();
        String rentArea = rentAreas.stream().map(i -> i.getValue().toString()).collect(Collectors.joining(","));
        BuildingDTO dto = modelMapper.map(buildingEntity, BuildingDTO.class);
        dto.setRentArea(rentArea);
        String type = buildingEntity.getTypeCode();
        List<String> typeCode = Stream.of(type.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        dto.setTypeCode(typeCode);
        return dto;
    }
}
