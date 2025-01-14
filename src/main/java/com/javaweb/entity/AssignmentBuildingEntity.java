package com.javaweb.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "assignmentbuilding")
@Getter
@Setter
public class AssignmentBuildingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingid")
    private BuildingEntity buildingEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staffid")
    private UserEntity userEntity;
}
