package com.javaweb.repository.custom.impl;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.enums.Status;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.custom.CustomerRepositoryCustom;
import com.javaweb.utils.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    private void sqlJoin(CustomerSearchRequest searchRequest,StringBuilder join) {
        Long staffId = searchRequest.getStaffId();
        if(StringUtils.check(staffId)){
            join.append(" join assignmentcustomer on assignmentcustomer.customerid = c.id ");
        }
    }
    private void sqlWhere(CustomerSearchRequest searchRequest,StringBuilder where) {
        Long staffId = searchRequest.getStaffId();
        if(StringUtils.check(staffId)){
            where.append(" and assignmentcustomer.staffid =  ").append(staffId);
        }
        String statusCode = searchRequest.getStatus();
        if(statusCode != null && !statusCode.isEmpty()){
            where.append(" and c.status like '%").append(Status.getStatusNameByKey(statusCode)).append("%'");
        }
        Field[] fields = CustomerSearchRequest.class.getDeclaredFields();
        for (Field it : fields) {
            it.setAccessible(true);
            Object value = null;
            try {
                value = it.get(searchRequest);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            String fieldName = it.getName();
            if (!fieldName.equals("staffId") && !fieldName.equals("status")) {
                if (value != null && !"".equals(value)) {
                    if (it.getType().getName().equals("java.lang.String")) {
                        where.append(" AND c.").append(fieldName).append(" LIKE '%").append(value).append("%'");
                    } else {
                        where.append(" AND c.").append(fieldName).append(" = ").append(value);
                    }
                }
            }
        }
        where.append(" and c.is_active = 1 ");
    }
    @Override
    public List<CustomerEntity> findAll(CustomerSearchRequest builder) {
        StringBuilder sql = new StringBuilder(" select c.* from customer c ");
        sqlJoin(builder, sql);
        StringBuilder where = new StringBuilder(" where 1=1 ");
        sqlWhere(builder, where);
        sql.append(where).append("group by c.id");
        if(builder.getStaffId() != null){
            sql.append(" , assignmentcustomer.id");
        }
        Query query = entityManager.createNativeQuery(sql.toString(),CustomerEntity.class);
        return query.getResultList();
    }
}
