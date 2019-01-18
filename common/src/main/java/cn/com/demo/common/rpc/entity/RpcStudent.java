package cn.com.demo.common.rpc.entity;

import java.util.Date;

/**
 * 学生信息
 * @author demo
 * Created by demo on 2018/3/14.
 */
public class RpcStudent extends RpcEntity {

    private Long id;
    private String name;
    private Long schoolId;
    private String school;
    private String grade;
    private String username;
    private int age;
    private String teacher;
    private String sex;
    private String province;
    private String city;
    private String parentName;
    private String parentEmail;
    private String parentMobile;
    private Date createDate;
    private String openid;
    private String wxNickName;
    private Boolean review;
    private RpcBuyRecord buyRecord;
    private Boolean canLearnAll;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Boolean getReview() {
        return review;
    }

    public void setReview(Boolean review) {
        this.review = review;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public RpcBuyRecord getBuyRecord() {
        return buyRecord;
    }

    public void setBuyRecord(RpcBuyRecord buyRecord) {
        this.buyRecord = buyRecord;
    }

    public Boolean getCanLearnAll() {
        return canLearnAll;
    }

    public void setCanLearnAll(Boolean canLearnAll) {
        this.canLearnAll = canLearnAll;
    }
}
