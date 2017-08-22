package main.java.cn.nanphonfy.mybatis.generator.entity;

import java.util.ArrayList;
import java.util.List;

public class TCourseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TCourseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCourseidIsNull() {
            addCriterion("courseId is null");
            return (Criteria) this;
        }

        public Criteria andCourseidIsNotNull() {
            addCriterion("courseId is not null");
            return (Criteria) this;
        }

        public Criteria andCourseidEqualTo(Integer value) {
            addCriterion("courseId =", value, "courseid");
            return (Criteria) this;
        }

        public Criteria andCourseidNotEqualTo(Integer value) {
            addCriterion("courseId <>", value, "courseid");
            return (Criteria) this;
        }

        public Criteria andCourseidGreaterThan(Integer value) {
            addCriterion("courseId >", value, "courseid");
            return (Criteria) this;
        }

        public Criteria andCourseidGreaterThanOrEqualTo(Integer value) {
            addCriterion("courseId >=", value, "courseid");
            return (Criteria) this;
        }

        public Criteria andCourseidLessThan(Integer value) {
            addCriterion("courseId <", value, "courseid");
            return (Criteria) this;
        }

        public Criteria andCourseidLessThanOrEqualTo(Integer value) {
            addCriterion("courseId <=", value, "courseid");
            return (Criteria) this;
        }

        public Criteria andCourseidIn(List<Integer> values) {
            addCriterion("courseId in", values, "courseid");
            return (Criteria) this;
        }

        public Criteria andCourseidNotIn(List<Integer> values) {
            addCriterion("courseId not in", values, "courseid");
            return (Criteria) this;
        }

        public Criteria andCourseidBetween(Integer value1, Integer value2) {
            addCriterion("courseId between", value1, value2, "courseid");
            return (Criteria) this;
        }

        public Criteria andCourseidNotBetween(Integer value1, Integer value2) {
            addCriterion("courseId not between", value1, value2, "courseid");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberIsNull() {
            addCriterion("grandientNumber is null");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberIsNotNull() {
            addCriterion("grandientNumber is not null");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberEqualTo(Integer value) {
            addCriterion("grandientNumber =", value, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberNotEqualTo(Integer value) {
            addCriterion("grandientNumber <>", value, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberGreaterThan(Integer value) {
            addCriterion("grandientNumber >", value, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("grandientNumber >=", value, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberLessThan(Integer value) {
            addCriterion("grandientNumber <", value, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberLessThanOrEqualTo(Integer value) {
            addCriterion("grandientNumber <=", value, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberIn(List<Integer> values) {
            addCriterion("grandientNumber in", values, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberNotIn(List<Integer> values) {
            addCriterion("grandientNumber not in", values, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberBetween(Integer value1, Integer value2) {
            addCriterion("grandientNumber between", value1, value2, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andGrandientnumberNotBetween(Integer value1, Integer value2) {
            addCriterion("grandientNumber not between", value1, value2, "grandientnumber");
            return (Criteria) this;
        }

        public Criteria andIstemplateIsNull() {
            addCriterion("isTemplate is null");
            return (Criteria) this;
        }

        public Criteria andIstemplateIsNotNull() {
            addCriterion("isTemplate is not null");
            return (Criteria) this;
        }

        public Criteria andIstemplateEqualTo(Boolean value) {
            addCriterion("isTemplate =", value, "istemplate");
            return (Criteria) this;
        }

        public Criteria andIstemplateNotEqualTo(Boolean value) {
            addCriterion("isTemplate <>", value, "istemplate");
            return (Criteria) this;
        }

        public Criteria andIstemplateGreaterThan(Boolean value) {
            addCriterion("isTemplate >", value, "istemplate");
            return (Criteria) this;
        }

        public Criteria andIstemplateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isTemplate >=", value, "istemplate");
            return (Criteria) this;
        }

        public Criteria andIstemplateLessThan(Boolean value) {
            addCriterion("isTemplate <", value, "istemplate");
            return (Criteria) this;
        }

        public Criteria andIstemplateLessThanOrEqualTo(Boolean value) {
            addCriterion("isTemplate <=", value, "istemplate");
            return (Criteria) this;
        }

        public Criteria andIstemplateIn(List<Boolean> values) {
            addCriterion("isTemplate in", values, "istemplate");
            return (Criteria) this;
        }

        public Criteria andIstemplateNotIn(List<Boolean> values) {
            addCriterion("isTemplate not in", values, "istemplate");
            return (Criteria) this;
        }

        public Criteria andIstemplateBetween(Boolean value1, Boolean value2) {
            addCriterion("isTemplate between", value1, value2, "istemplate");
            return (Criteria) this;
        }

        public Criteria andIstemplateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isTemplate not between", value1, value2, "istemplate");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCoachidIsNull() {
            addCriterion("coachID is null");
            return (Criteria) this;
        }

        public Criteria andCoachidIsNotNull() {
            addCriterion("coachID is not null");
            return (Criteria) this;
        }

        public Criteria andCoachidEqualTo(Integer value) {
            addCriterion("coachID =", value, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoachidNotEqualTo(Integer value) {
            addCriterion("coachID <>", value, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoachidGreaterThan(Integer value) {
            addCriterion("coachID >", value, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoachidGreaterThanOrEqualTo(Integer value) {
            addCriterion("coachID >=", value, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoachidLessThan(Integer value) {
            addCriterion("coachID <", value, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoachidLessThanOrEqualTo(Integer value) {
            addCriterion("coachID <=", value, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoachidIn(List<Integer> values) {
            addCriterion("coachID in", values, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoachidNotIn(List<Integer> values) {
            addCriterion("coachID not in", values, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoachidBetween(Integer value1, Integer value2) {
            addCriterion("coachID between", value1, value2, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoachidNotBetween(Integer value1, Integer value2) {
            addCriterion("coachID not between", value1, value2, "coachid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidIsNull() {
            addCriterion("courseTypeID is null");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidIsNotNull() {
            addCriterion("courseTypeID is not null");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidEqualTo(Integer value) {
            addCriterion("courseTypeID =", value, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidNotEqualTo(Integer value) {
            addCriterion("courseTypeID <>", value, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidGreaterThan(Integer value) {
            addCriterion("courseTypeID >", value, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidGreaterThanOrEqualTo(Integer value) {
            addCriterion("courseTypeID >=", value, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidLessThan(Integer value) {
            addCriterion("courseTypeID <", value, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidLessThanOrEqualTo(Integer value) {
            addCriterion("courseTypeID <=", value, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidIn(List<Integer> values) {
            addCriterion("courseTypeID in", values, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidNotIn(List<Integer> values) {
            addCriterion("courseTypeID not in", values, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidBetween(Integer value1, Integer value2) {
            addCriterion("courseTypeID between", value1, value2, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCoursetypeidNotBetween(Integer value1, Integer value2) {
            addCriterion("courseTypeID not between", value1, value2, "coursetypeid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidIsNull() {
            addCriterion("currentGrandient_grandientId is null");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidIsNotNull() {
            addCriterion("currentGrandient_grandientId is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidEqualTo(Integer value) {
            addCriterion("currentGrandient_grandientId =", value, "currentgrandientGrandientid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidNotEqualTo(Integer value) {
            addCriterion("currentGrandient_grandientId <>", value, "currentgrandientGrandientid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidGreaterThan(Integer value) {
            addCriterion("currentGrandient_grandientId >", value, "currentgrandientGrandientid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidGreaterThanOrEqualTo(Integer value) {
            addCriterion("currentGrandient_grandientId >=", value, "currentgrandientGrandientid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidLessThan(Integer value) {
            addCriterion("currentGrandient_grandientId <", value, "currentgrandientGrandientid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidLessThanOrEqualTo(Integer value) {
            addCriterion("currentGrandient_grandientId <=", value, "currentgrandientGrandientid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidIn(List<Integer> values) {
            addCriterion("currentGrandient_grandientId in", values, "currentgrandientGrandientid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidNotIn(List<Integer> values) {
            addCriterion("currentGrandient_grandientId not in", values, "currentgrandientGrandientid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidBetween(Integer value1, Integer value2) {
            addCriterion("currentGrandient_grandientId between", value1, value2, "currentgrandientGrandientid");
            return (Criteria) this;
        }

        public Criteria andCurrentgrandientGrandientidNotBetween(Integer value1, Integer value2) {
            addCriterion("currentGrandient_grandientId not between", value1, value2, "currentgrandientGrandientid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}