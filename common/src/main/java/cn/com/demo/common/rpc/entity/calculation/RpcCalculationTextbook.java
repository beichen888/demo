package cn.com.demo.common.rpc.entity.calculation;

import cn.com.demo.common.rpc.entity.RpcEntity;

public class RpcCalculationTextbook extends RpcEntity {

    private String name;
    private Integer value;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
