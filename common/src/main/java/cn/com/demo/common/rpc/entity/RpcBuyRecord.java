package cn.com.demo.common.rpc.entity;

import java.util.Date;

/**
 * Created by demo on 2018/3/15.
 */
public class RpcBuyRecord extends RpcEntity {
    /**
     * 学生id
     */
    private Long studentId;
    /**
     * 课时数
     */
    private Integer lessonNum;

    /**
     * 学习课时数
     */
    private Integer studyNum;

    /**
     * 开始时间和过期时间，购买期限的课程用到
     */
    private Date startDate;
    private Date endDate;


    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public Integer getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(Integer studyNum) {
        this.studyNum = studyNum;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
