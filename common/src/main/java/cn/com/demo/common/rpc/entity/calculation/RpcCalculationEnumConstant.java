package cn.com.demo.common.rpc.entity.calculation;

/**
 * Created by demo on 2018/3/6.
 */
public enum RpcCalculationEnumConstant {

    FILL("填空题"), CHECKBOX("打钩题"), CALCULATION("计算题");//题型

    RpcCalculationEnumConstant(String value) {
        this.value = value;
    }

    /**
     * 名称
     */
    private String value;

    public String getValue() {
        return value;
    }

    public static RpcCalculationEnumConstant parse(String gradeName) {
        for (RpcCalculationEnumConstant enumConstant : values()) {
            if (enumConstant.getValue().equals(gradeName)) {
                return enumConstant;
            }
        }
        return null;
    }
}
