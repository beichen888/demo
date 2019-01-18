package cn.com.demo.common.rpc.entity.calculation;

import cn.com.demo.common.rpc.entity.RpcEntity;

import java.util.Date;
import java.util.List;

public class RpcCalculationEntryTest extends RpcEntity {

    private Long id;
    private Long studentId;
    private Date startTime;
    private Date endTime;
    private Integer total;
    private Integer correct;

    private List<RpcCalculationQuestionResult> resultList;

    private RpcLearnProgress startBook;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public List<RpcCalculationQuestionResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<RpcCalculationQuestionResult> resultList) {
        this.resultList = resultList;
    }

    public RpcLearnProgress getStartBook() {
        return startBook;
    }

    public void setStartBook(RpcLearnProgress startBook) {
        this.startBook = startBook;
    }
}