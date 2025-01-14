package com.javaweb.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rentarea")
@Getter
@Setter
public class RentAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "value")
    private Long value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingid")
    private BuildingEntity buildingEntity;
}
