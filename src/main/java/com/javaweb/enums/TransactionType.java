package com.javaweb.enums;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum TransactionType {
    CSKH("Chăm sóc khách hàng"),
    DDX("Dẫn đi xem nhà");
    private String transactionType;
    TransactionType(String name){
        this.transactionType = name;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public static Map<String,String> transactionTypes() {
        Map<String,String> map = new LinkedHashMap<>();
        for(TransactionType type : TransactionType.values()) {
            map.put(type.toString(), type.transactionType);
        }
        return map;
    }

}
