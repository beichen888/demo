package cn.com.demo.api.entity;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by miguo on 2018/3/6.
 */
public enum EnumConstant {
    ADMIN("管理员"), USER("用户");//用户权限


    EnumConstant(String value) {
        this.value = value;
    }

    /**
     * 名称
     */
    private String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public static EnumConstant parse(String constantName) {
        if(constantName == null || constantName.isEmpty()){
            return null;
        }
        for (EnumConstant enumConstant : values()) {
            if (enumConstant.getValue().equals(constantName)) {
                return enumConstant;
            }
        }
        return null;
    }
}
