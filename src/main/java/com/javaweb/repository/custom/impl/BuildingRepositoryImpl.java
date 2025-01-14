package com.javaweb.repository.custom.impl;

import com.javaweb.Builder.BuildingSearchBuilder;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.utils.NumberUtils;
import com.javaweb.utils.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    private void sqlJoin(BuildingSearchBuilder builder, StringBuilder join) {
        Long staffId = builder.getStaffId();
        if(StringUtils.check(staffId)) {
            join.append(" join assignmentbuilding ab on b.id = ab.buidingid ");
        }
        Long rentAreaFrom = builder.getAreaFrom();
        Long rentAreaTo = builder.getAreaTo();
        if(StringUtils.check(rentAreaFrom) || StringUtils.check(rentAreaTo)) {
            join.append(" Join rentArea rt on rt.buildingId = b.id ");
        }
    }
    private void sqlWhereNomal(BuildingSearchBuilder builder , StringBuilder where) {
        try {
            Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
            for(Field it : fields) {
                it.setAccessible(true);
                String fileName = it.getName();
                if(!fileName.equals("staffId") && !fileName.equals("typeCode") && !fileName.startsWith("area") && !fileName.startsWith("rentPrice")) {
                    Object value = it.get(builder);
                    if(value != null) {
                        if(it.getType().getName().equals("java.lang.String")) {
                            where.append(" And b." + fileName + " Like'%" + value + "%'");
                        }
                        else if(it.getType().getName().equals("java.lang.Long") || it.getType().equals("java.lang.Integer")) {
                            where.append(" And b." + fileName + " = " + value );
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void sqlWhereSpecial(BuildingSearchBuilder builder, StringBuilder where) {
        Long staffId = builder.getStaffId();
        if (StringUtils.check(staffId)) {
            where.append(" and ab.staffid =  " + staffId);
        }
        Long rentAreaFrom = builder.getAreaFrom();
        Long rentAreaTo = builder.getAreaTo();
        if (StringUtils.check(rentAreaFrom)) {
            where.append(" and rt.value >= " + rentAreaFrom);
            System.out.println("hello");
        }
        if (StringUtils.check(rentAreaTo)) {
            where.append(" and rt.value <= " + rentAreaTo);
        }
        Long rentPriceFrom = builder.getRentPriceFrom();
        Long rentPriceTo = builder.getRentPriceTo();
        if (StringUtils.check(rentPriceFrom)) {
            where.append(" and b.rentprice >= " + rentPriceFrom);
        }
        if (StringUtils.check(rentPriceTo)) {
            where.append(" and b.rentprice <= " + rentPriceTo);
        }
        List<String> typeCode = builder.getTypeCode();
        if (typeCode != null && !typeCode.isEmpty()) {
            // Java 8
            where.append(" and b.type in( " + typeCode.stream().map(i -> "'" + i + "'").collect(Collectors.joining(",")) + ")");
        }
    }

    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder builder) {
        StringBuilder sql = new StringBuilder("select b.* from Building b ");
        sqlJoin(builder, sql);
        StringBuilder where = new StringBuilder(" where 1=1 ");
        sqlWhereNomal(builder, where);
        sqlWhereSpecial(builder, where);
        sql.append(where).append(" Group by b.id ");
        if(builder.getStaffId() != null) {
            sql.append(" , ab.id ");
        }
        Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
        return query.getResultList();
    }
}
