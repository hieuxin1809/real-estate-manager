package com.javaweb.repository.custom;

import com.javaweb.Builder.BuildingSearchBuilder;
import com.javaweb.entity.BuildingEntity;

import java.util.List;

public interface BuildingRepositoryCustom {
    List<BuildingEntity> findAll(BuildingSearchBuilder builder);
}
