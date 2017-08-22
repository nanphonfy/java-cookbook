package main.java.cn.nanphonfy.mybatis.generator.entity;

public class TCourse {
    private Integer courseid;

    private Integer grandientnumber;

    private Boolean istemplate;

    private String remark;

    private String status;

    private Integer coachid;

    private Integer coursetypeid;

    private Integer currentgrandientGrandientid;

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public Integer getGrandientnumber() {
        return grandientnumber;
    }

    public void setGrandientnumber(Integer grandientnumber) {
        this.grandientnumber = grandientnumber;
    }

    public Boolean getIstemplate() {
        return istemplate;
    }

    public void setIstemplate(Boolean istemplate) {
        this.istemplate = istemplate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getCoachid() {
        return coachid;
    }

    public void setCoachid(Integer coachid) {
        this.coachid = coachid;
    }

    public Integer getCoursetypeid() {
        return coursetypeid;
    }

    public void setCoursetypeid(Integer coursetypeid) {
        this.coursetypeid = coursetypeid;
    }

    public Integer getCurrentgrandientGrandientid() {
        return currentgrandientGrandientid;
    }

    public void setCurrentgrandientGrandientid(Integer currentgrandientGrandientid) {
        this.currentgrandientGrandientid = currentgrandientGrandientid;
    }
}