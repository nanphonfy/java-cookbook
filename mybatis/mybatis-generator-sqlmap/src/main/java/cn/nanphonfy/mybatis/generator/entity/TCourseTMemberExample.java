package main.java.cn.nanphonfy.mybatis.generator.entity;

import java.util.ArrayList;
import java.util.List;

public class TCourseTMemberExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TCourseTMemberExample() {
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

        public Criteria andCoursesCourseidIsNull() {
            addCriterion("courses_courseId is null");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidIsNotNull() {
            addCriterion("courses_courseId is not null");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidEqualTo(Integer value) {
            addCriterion("courses_courseId =", value, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidNotEqualTo(Integer value) {
            addCriterion("courses_courseId <>", value, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidGreaterThan(Integer value) {
            addCriterion("courses_courseId >", value, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidGreaterThanOrEqualTo(Integer value) {
            addCriterion("courses_courseId >=", value, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidLessThan(Integer value) {
            addCriterion("courses_courseId <", value, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidLessThanOrEqualTo(Integer value) {
            addCriterion("courses_courseId <=", value, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidIn(List<Integer> values) {
            addCriterion("courses_courseId in", values, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidNotIn(List<Integer> values) {
            addCriterion("courses_courseId not in", values, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidBetween(Integer value1, Integer value2) {
            addCriterion("courses_courseId between", value1, value2, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andCoursesCourseidNotBetween(Integer value1, Integer value2) {
            addCriterion("courses_courseId not between", value1, value2, "coursesCourseid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidIsNull() {
            addCriterion("members_memberId is null");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidIsNotNull() {
            addCriterion("members_memberId is not null");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidEqualTo(Integer value) {
            addCriterion("members_memberId =", value, "membersMemberid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidNotEqualTo(Integer value) {
            addCriterion("members_memberId <>", value, "membersMemberid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidGreaterThan(Integer value) {
            addCriterion("members_memberId >", value, "membersMemberid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidGreaterThanOrEqualTo(Integer value) {
            addCriterion("members_memberId >=", value, "membersMemberid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidLessThan(Integer value) {
            addCriterion("members_memberId <", value, "membersMemberid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidLessThanOrEqualTo(Integer value) {
            addCriterion("members_memberId <=", value, "membersMemberid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidIn(List<Integer> values) {
            addCriterion("members_memberId in", values, "membersMemberid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidNotIn(List<Integer> values) {
            addCriterion("members_memberId not in", values, "membersMemberid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidBetween(Integer value1, Integer value2) {
            addCriterion("members_memberId between", value1, value2, "membersMemberid");
            return (Criteria) this;
        }

        public Criteria andMembersMemberidNotBetween(Integer value1, Integer value2) {
            addCriterion("members_memberId not between", value1, value2, "membersMemberid");
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