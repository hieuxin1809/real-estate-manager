package com.javaweb.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum TypeCode {
    TANG_TRET("Tầng Trệt"),
    NGUYEN_CAN("Nguyên Căn"),
    NOI_THAT("Nội Thất");
    private String typeName;
    TypeCode(String typeName) {
        this.typeName = typeName;
    }
    public String getTypeName() {
        return typeName;
    }
    public static Map<String , String> getType(){
        Map<String , String> types = new LinkedHashMap<>();
        for (TypeCode d : TypeCode.values()) {
            types.put(d.toString(), d.getTypeName());
        }
        return types;
    }
}
