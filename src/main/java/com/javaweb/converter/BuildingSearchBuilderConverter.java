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
        BuildingSearchBuilder buildingSearchBuilder = BuildingSearchBuilder.builder()
                .name(MapUtils.getObject(params.getName(), String.class))
                .district(MapUtils.getObject(params.getDistrict(), String.class))
                .areaFrom(MapUtils.getObject(params.getAreaFrom(), Long.class))
                .areaTo(MapUtils.getObject(params.getAreaTo(), Long.class))
                .floorArea(MapUtils.getObject(params.getFloorArea(), Long.class))
                .managerName(MapUtils.getObject(params.getManagerName(), String.class))
                .managerPhone(MapUtils.getObject(params.getManagerPhoneNumber(), String.class))
                .typeCode(params.getTypeCode())
                .rentPriceFrom(MapUtils.getObject(params.getRentPriceFrom(), Long.class))
                .rentPriceTo(MapUtils.getObject(params.getRentPriceTo(), Long.class))
                .numberOfBasement(MapUtils.getObject(params.getNumberOfBasement(), Long.class))
                .staffId(MapUtils.getObject(params.getStaffId(), Long.class))
                .street(MapUtils.getObject(params.getStreet(), String.class))
                .ward(MapUtils.getObject(params.getWard(), String.class))
                .build();
        return buildingSearchBuilder;
    }
}
