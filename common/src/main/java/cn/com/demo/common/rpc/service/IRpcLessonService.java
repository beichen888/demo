package cn.com.demo.common.rpc.service;

import cn.com.demo.common.exception.AppException;

/**
 * 课时管理 RPC接口
 * Created by demo on 2018/3/15.
 */
public interface IRpcLessonService {

    public static final String LESSION_SERVICE = "/remote/lessonService";
    /**
     * 消耗课时数
     * reason 所花课时的知识点名称
     */
    void reduceLessonCnt(Long studentId, Long subjectId, String reason) throws AppException;
}
