package com.javaweb.enums;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum Status {
    CHUA_XU_LY("Chưa xử lý"),
    DANG_XU_LY("Đang xử lý"),
    DA_XU_LY("Đã xử lý");

    private final String statusName;
    Status(String statusname) {
        this.statusName = statusname;
    }
    public String getStatusName() {
        return statusName;
    }
    public static String getStatusNameByKey(String key) {
        try {
            return Status.valueOf(key).getStatusName();
        } catch (IllegalArgumentException e) {
            return "Trạng thái không hợp lệ!";
        }
    }

    public static String getKeyByStatusName(String statusName) {
        for (Status status : Status.values()) {
            if (status.getStatusName().equalsIgnoreCase(statusName)) {
                return status.name(); // Trả về tên Enum (key)
            }
        }
        return "Không tìm thấy trạng thái!";
    }
    public static Map<String,String> statusType(){
        Map<String,String> map = new LinkedHashMap<>();
        for(Status status : Status.values()){
            map.put(status.toString(),status.statusName);
        }
        return map;
    }
}
