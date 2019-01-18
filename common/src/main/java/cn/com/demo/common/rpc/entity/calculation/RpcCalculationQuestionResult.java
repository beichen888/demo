package cn.com.demo.common.rpc.entity.calculation;

import cn.com.demo.common.rpc.entity.RpcEntity;

import java.util.List;

public class RpcCalculationQuestionResult extends RpcEntity {

    private RpcCalculationEntryQuestion question;

    private Integer iorder;

    private List<String> answerList;

    private List<Integer> selectCommentList;

    private Boolean correct;
    private Boolean partCorrect;

    private Long testId;

    private Integer consume;//秒 最后一题用时

    public RpcCalculationEntryQuestion getQuestion() {
        return question;
    }

    public void setQuestion(RpcCalculationEntryQuestion question) {
        this.question = question;
    }

    public Integer getIorder() {
        return iorder;
    }

    public void setIorder(Integer iorder) {
        this.iorder = iorder;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public List<Integer> getSelectCommentList() {
        return selectCommentList;
    }

    public void setSelectCommentList(List<Integer> selectCommentList) {
        this.selectCommentList = selectCommentList;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Boolean getPartCorrect() {
        return partCorrect;
    }

    public void setPartCorrect(Boolean partCorrect) {
        this.partCorrect = partCorrect;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Integer getConsume() {
        return consume;
    }

    public void setConsume(Integer consume) {
        this.consume = consume;
    }
}
