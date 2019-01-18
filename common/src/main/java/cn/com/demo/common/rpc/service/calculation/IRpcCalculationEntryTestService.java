package cn.com.demo.common.rpc.service.calculation;

import cn.com.demo.common.rpc.entity.calculation.RpcCalculationEntryTest;

public interface IRpcCalculationEntryTestService {

    public static final String CALCULATION_ENTRY_TEST_SERVICE = "/remote/calculationEntryTestService";

    RpcCalculationEntryTest findByStudent(Long studentId);
}
