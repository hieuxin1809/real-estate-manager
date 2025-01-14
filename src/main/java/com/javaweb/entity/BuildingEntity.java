package com.javaweb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "building")
public class BuildingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "floorarea")
    private Long floorArea;
    @Column(name = "ward")
    private String ward;
    @Column(name = "street")
    private String street;
    @Column(name = "numberofbasement")
    private Long numberOfBasement;
    @Column(name = "direction")
    private String direction;
    @Column(name = "level")
    private Long level;
    @Column(name = "managername")
    private String managerName;
    @Column(name = "managerphone")
    private String managerPhoneNumber;
    @Column(name = "district")
    private String district;
    @OneToMany(mappedBy = "buildingEntity", fetch = FetchType.LAZY)
    private List<RentAreaEntity> rentAreas = new ArrayList<RentAreaEntity>();
    @OneToMany(mappedBy = "buildingEntity", fetch = FetchType.LAZY)
    private List<AssignmentBuildingEntity> assignmentBuildingEntities = new ArrayList<>();
    @Column(name = "structure")
    private String structure;
    @Column(name = "rentprice")
    private Long rentPrice;
    @Column(name = "rentpricedescription")
    private String rentPriceDescription;
    @Column(name = "servicefee")
    private Long serviceFee;
    @Column(name = "carfee")
    private Long carFee;
    @Column(name = "motofee")
    private Long motoFee;
    @Column(name = "overtimefee")
    private Long overTimeFee;
    @Column(name = "waterfee")
    private Long waterFee;
    @Column(name = "electricityfee")
    private Long electricityFee;
    @Column(name = "deposit")
    private Long deposit;
    @Column(name = "payment")
    private Long payment;
    @Column(name = "renttime")
    private Long rentTime;
    @Column(name = "decorationtime")
    private Long decorationTime;
    @Column(name = "brokeragefee")
    private Long brokerageFee;
    @Column(name = "type")
    private String typeCode;
}
