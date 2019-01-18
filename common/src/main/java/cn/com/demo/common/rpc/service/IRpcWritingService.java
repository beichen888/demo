package cn.com.demo.common.rpc.service;

public interface IRpcWritingService {
    public static final String STUDENT_SERVICE = "/remote/writingService";

    Long getCurrentClassId(Long teacherId);
}
