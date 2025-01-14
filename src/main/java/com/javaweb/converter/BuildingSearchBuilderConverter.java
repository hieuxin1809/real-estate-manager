package com.javaweb.converter;

import com.javaweb.Builder.BuildingSearchBuilder;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.utils.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BuildingSearchBuilderConverter {
    public BuildingSearchBuilder toBuildingSearchBuilder(BuildingSearchRequest params) {
        BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
                .setName(MapUtils.getObject(params.getName(), String.class))
                .setDistrict(MapUtils.getObject(params.getDistrict(), String.class))
                .setAreaFrom(MapUtils.getObject(params.getAreaFrom(), Long.class))
                .setAreaTo(MapUtils.getObject(params.getAreaTo(), Long.class))
                .setFloorArea(MapUtils.getObject(params.getFloorArea(), Long.class))
                .setManagerName(MapUtils.getObject(params.getManagerName(), String.class))
                .setManagerPhone(MapUtils.getObject(params.getManagerPhoneNumber(), String.class))
                .setTypeCode(params.getTypeCode())
                .setRentPriceFrom(MapUtils.getObject(params.getRentPriceFrom(), Long.class))
                .setRentPriceTo(MapUtils.getObject(params.getRentPriceTo(), Long.class))
                .setNumberOfBasement(MapUtils.getObject(params.getNumberOfBasement(), Long.class))
                .setStaffId(MapUtils.getObject(params.getStaffId(), Long.class))
                .setStreet(MapUtils.getObject(params.getStreet(), String.class))
                .setWard(MapUtils.getObject(params.getWard(), String.class))
                .build();
        return buildingSearchBuilder;
    }
}
