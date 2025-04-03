package com.javaweb.Builder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class BuildingSearchBuilder {
    private String name;
    private Long floorArea;
    private String ward;
    private String street;
    private String district;
    private Long numberOfBasement;
    private List<String> typeCode = new ArrayList<>();
    private String managerName;
    private String managerPhone;
    private Long rentPriceFrom;
    private Long rentPriceTo;
    private Long areaFrom;
    private Long areaTo;
    private Long staffId;
}
