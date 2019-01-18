package cn.com.demo.common.rpc.service.card;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.common.rpc.entity.RpcWxAppSchool;

import java.util.List;

public interface IRpcCardSchoolService {
    public static final String CARD_SCHOOL_SERVICE = "remote/cardSchoolService";

    void dataSync(RpcWxAppSchool school) throws AppException;
    void dataSyncAll(List<RpcWxAppSchool> schoolList) throws AppException;
}
