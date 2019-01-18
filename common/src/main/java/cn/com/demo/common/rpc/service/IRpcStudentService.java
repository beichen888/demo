package cn.com.demo.common.rpc.service;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.common.rpc.entity.RpcBuyRecord;
import cn.com.demo.common.rpc.entity.RpcStudent;

import java.util.List;
import java.util.Map;

/**
 * 学生信息RPC接口
 * Created by demo on 2018/3/14.
 */
public interface IRpcStudentService {

    public static final String STUDENT_SERVICE = "/remote/studentService";

    RpcStudent getStudentInfo(Long studentId);

    /**
     * 添加金币
     * @param studentId 学生id
     * @param num 金币数
     */
    void addCoinNum(Long studentId, Integer num);

    /**
     * 获取学生课时数和已用课时数，无购买记录或无可用课时时返回null
     * @param studentId 学生id
     * @param subjectId 科目id
     * @return
     */
    RpcBuyRecord getStudentBuyRecord(Long studentId, Long subjectId);

    /**
     * 更新思维课上课时间
     * @param studentId 学生id
     */
    void updateThinkLastLearnDate(Long studentId);

    /**
     * 更新识字课上课时间
     * @param studentId 学生id
     */
    void updateLiteracyLastLearnDate(Long studentId);

    /**
     * 更新写作课上课时间
     * @param studentId 学生id
     */
    void updateWritingLastLearnDate(Long studentId);

    /**
     * 获取所有测试学校的学生id
     * @return
     */
    List<Long> listIdsByTestSchool();

    /**
     * 通过学校id获取某个学校的学生id
     * @param schoolId
     * @return
     */
    List<Long> listIdsBySchoolId(Long schoolId);

    /**
     * 学生是否有有效的购买记录
     * @param studentId,subjectId
     * @return
     */
    Boolean haveValidBuyRecord(Long studentId, Long subjectId);

    /**
     * 通过学生id获取mac地址
     * @param studentId
     * @return
     */
    String getLoginMac(Long studentId);

    //柚柚心算获取绑定学生信息
    Map<String, Object> getBindStudentInfo(Long studentId);
    //柚柚心算验证绑定用户
    Map<String, Object> userPasswordAuth(String username, String password);

    String userPasswordAuthReturnJsonStr(String username, String password, List<Long> subjectIds) throws AppException;
    String getBindStudentInfoReturnJsonStr(Long studentId, List<Long> subjectIds);
    String getStudentListReturnJsonStr(List<Long> ids, List<Long> subjectIds);

    /**
     * 根据学生ids找出学生信息，包含购买记录信息，没有课时时student的buyRecord为null
     * @param ids
     * @param subjectId
     * @return
     */
    List<RpcStudent> getStudentInfoByIds(List<Long> ids, Long subjectId);
}
