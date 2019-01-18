package cn.com.demo.common.rpc.entity.calculation;

import cn.com.demo.common.rpc.entity.RpcEntity;

public class RpcLearnProgress extends RpcEntity {

    private RpcCalculationTextbook start;
    private RpcCalculationTextbook threeMonth;
    private RpcCalculationTextbook sixMonth;
    private RpcCalculationTextbook oneYear;

    public RpcCalculationTextbook getStart() {
        return start;
    }

    public void setStart(RpcCalculationTextbook start) {
        this.start = start;
    }

    public RpcCalculationTextbook getThreeMonth() {
        return threeMonth;
    }

    public void setThreeMonth(RpcCalculationTextbook threeMonth) {
        this.threeMonth = threeMonth;
    }

    public RpcCalculationTextbook getSixMonth() {
        return sixMonth;
    }

    public void setSixMonth(RpcCalculationTextbook sixMonth) {
        this.sixMonth = sixMonth;
    }

    public RpcCalculationTextbook getOneYear() {
        return oneYear;
    }

    public void setOneYear(RpcCalculationTextbook oneYear) {
        this.oneYear = oneYear;
    }
}