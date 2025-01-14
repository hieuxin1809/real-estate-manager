package com.javaweb.converter;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.enums.District;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.response.BuildingSearchResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BuildingConverter {
    @Autowired
    private ModelMapper modelMapper;
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
        return bdDto;
    }
    public BuildingEntity toBuildingEntity(BuildingDTO bdDto) {
        BuildingEntity buildingEntity = modelMapper.map(bdDto, BuildingEntity.class);
        String result = bdDto.getTypeCode().stream().collect(Collectors.joining(","));
        buildingEntity.setTypeCode(result);
        return buildingEntity;
    }
}
