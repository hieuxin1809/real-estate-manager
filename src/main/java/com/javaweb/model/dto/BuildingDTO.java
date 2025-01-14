package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class BuildingDTO extends AbstractDTO{
    @NotBlank(message = "name can be not blank")
    private String name;
    @NotBlank(message = "street can be not bank")
    private String street;
    @NotBlank(message = "ward can be not bank")
    private String ward;
    @NotBlank(message = "ditrict can be not blank")
    private String district;
    @Min(value = 1,message = "nnumberOfBasement need larger than zero")
    private Long numberOfBasement;
    private Long floorArea;
    private String level;
    @Size(min = 1,message = "typeCode is Required")
    private List<String> typeCode;
    private String overtimeFee;
    private String electricityFee;
    private String deposit;
    private String payment;
    private String rentTime;
    private String decorationTime;
    private String rentPriceDescription;
    private String carFee;
    private String motoFee;
    private String structure;
    private String direction;
    private String note;
    private String rentArea;
    private String managerName;
    private String managerPhone;
    @NotNull(message = "rentPrice can be not null")
    private Long rentPrice;
    private String serviceFee;
    private double brokerageFee;
    private String image;
    private String waterFee;
    private String imageBase64;
    private String imageName;

    private Map<String,String> buildingDTOs = new HashMap<>();

    public String getImageBase64() {
        if (imageBase64 != null) {
            return imageBase64.split(",")[1];
        }
        return null;
    }
}