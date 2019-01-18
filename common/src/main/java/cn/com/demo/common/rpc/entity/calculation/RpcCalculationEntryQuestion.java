package cn.com.demo.common.rpc.entity.calculation;

import cn.com.demo.common.rpc.entity.RpcEntity;

import java.util.List;

public class RpcCalculationEntryQuestion extends RpcEntity {

    private String questionNo;

    private RpcCalculationEnumConstant type;

    private String content;

    private String stem;

    private List<String> stemList;

    private List<String> commentList;

    private List<String> answerList;

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public RpcCalculationEnumConstant getType() {
        return type;
    }

    public void setType(RpcCalculationEnumConstant type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public List<String> getStemList() {
        return stemList;
    }

    public void setStemList(List<String> stemList) {
        this.stemList = stemList;
    }

    public List<String> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<String> commentList) {
        this.commentList = commentList;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }
}
