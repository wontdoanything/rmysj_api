package org.rmysj.api.api.user.domain;

import org.rmysj.api.commons.domain.AbstractCriteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RobotUserCriteria extends AbstractCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RobotUserCriteria() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserAppellIsNull() {
            addCriterion("user_appell is null");
            return (Criteria) this;
        }

        public Criteria andUserAppellIsNotNull() {
            addCriterion("user_appell is not null");
            return (Criteria) this;
        }

        public Criteria andUserAppellEqualTo(String value) {
            addCriterion("user_appell =", value, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellNotEqualTo(String value) {
            addCriterion("user_appell <>", value, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellGreaterThan(String value) {
            addCriterion("user_appell >", value, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellGreaterThanOrEqualTo(String value) {
            addCriterion("user_appell >=", value, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellLessThan(String value) {
            addCriterion("user_appell <", value, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellLessThanOrEqualTo(String value) {
            addCriterion("user_appell <=", value, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellLike(String value) {
            addCriterion("user_appell like", value, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellNotLike(String value) {
            addCriterion("user_appell not like", value, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellIn(List<String> values) {
            addCriterion("user_appell in", values, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellNotIn(List<String> values) {
            addCriterion("user_appell not in", values, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellBetween(String value1, String value2) {
            addCriterion("user_appell between", value1, value2, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserAppellNotBetween(String value1, String value2) {
            addCriterion("user_appell not between", value1, value2, "userAppell");
            return (Criteria) this;
        }

        public Criteria andUserSexIsNull() {
            addCriterion("user_sex is null");
            return (Criteria) this;
        }

        public Criteria andUserSexIsNotNull() {
            addCriterion("user_sex is not null");
            return (Criteria) this;
        }

        public Criteria andUserSexEqualTo(String value) {
            addCriterion("user_sex =", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexNotEqualTo(String value) {
            addCriterion("user_sex <>", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexGreaterThan(String value) {
            addCriterion("user_sex >", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexGreaterThanOrEqualTo(String value) {
            addCriterion("user_sex >=", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexLessThan(String value) {
            addCriterion("user_sex <", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexLessThanOrEqualTo(String value) {
            addCriterion("user_sex <=", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexLike(String value) {
            addCriterion("user_sex like", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexNotLike(String value) {
            addCriterion("user_sex not like", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexIn(List<String> values) {
            addCriterion("user_sex in", values, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexNotIn(List<String> values) {
            addCriterion("user_sex not in", values, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexBetween(String value1, String value2) {
            addCriterion("user_sex between", value1, value2, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexNotBetween(String value1, String value2) {
            addCriterion("user_sex not between", value1, value2, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserCardIsNull() {
            addCriterion("user_card is null");
            return (Criteria) this;
        }

        public Criteria andUserCardIsNotNull() {
            addCriterion("user_card is not null");
            return (Criteria) this;
        }

        public Criteria andUserCardEqualTo(String value) {
            addCriterion("user_card =", value, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardNotEqualTo(String value) {
            addCriterion("user_card <>", value, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardGreaterThan(String value) {
            addCriterion("user_card >", value, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardGreaterThanOrEqualTo(String value) {
            addCriterion("user_card >=", value, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardLessThan(String value) {
            addCriterion("user_card <", value, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardLessThanOrEqualTo(String value) {
            addCriterion("user_card <=", value, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardLike(String value) {
            addCriterion("user_card like", value, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardNotLike(String value) {
            addCriterion("user_card not like", value, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardIn(List<String> values) {
            addCriterion("user_card in", values, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardNotIn(List<String> values) {
            addCriterion("user_card not in", values, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardBetween(String value1, String value2) {
            addCriterion("user_card between", value1, value2, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserCardNotBetween(String value1, String value2) {
            addCriterion("user_card not between", value1, value2, "userCard");
            return (Criteria) this;
        }

        public Criteria andUserFaceIsNull() {
            addCriterion("user_face is null");
            return (Criteria) this;
        }

        public Criteria andUserFaceIsNotNull() {
            addCriterion("user_face is not null");
            return (Criteria) this;
        }

        public Criteria andUserFaceEqualTo(String value) {
            addCriterion("user_face =", value, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceNotEqualTo(String value) {
            addCriterion("user_face <>", value, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceGreaterThan(String value) {
            addCriterion("user_face >", value, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceGreaterThanOrEqualTo(String value) {
            addCriterion("user_face >=", value, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceLessThan(String value) {
            addCriterion("user_face <", value, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceLessThanOrEqualTo(String value) {
            addCriterion("user_face <=", value, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceLike(String value) {
            addCriterion("user_face like", value, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceNotLike(String value) {
            addCriterion("user_face not like", value, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceIn(List<String> values) {
            addCriterion("user_face in", values, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceNotIn(List<String> values) {
            addCriterion("user_face not in", values, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceBetween(String value1, String value2) {
            addCriterion("user_face between", value1, value2, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserFaceNotBetween(String value1, String value2) {
            addCriterion("user_face not between", value1, value2, "userFace");
            return (Criteria) this;
        }

        public Criteria andUserAgeIsNull() {
            addCriterion("user_age is null");
            return (Criteria) this;
        }

        public Criteria andUserAgeIsNotNull() {
            addCriterion("user_age is not null");
            return (Criteria) this;
        }

        public Criteria andUserAgeEqualTo(Short value) {
            addCriterion("user_age =", value, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAgeNotEqualTo(Short value) {
            addCriterion("user_age <>", value, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAgeGreaterThan(Short value) {
            addCriterion("user_age >", value, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAgeGreaterThanOrEqualTo(Short value) {
            addCriterion("user_age >=", value, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAgeLessThan(Short value) {
            addCriterion("user_age <", value, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAgeLessThanOrEqualTo(Short value) {
            addCriterion("user_age <=", value, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAgeIn(List<Short> values) {
            addCriterion("user_age in", values, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAgeNotIn(List<Short> values) {
            addCriterion("user_age not in", values, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAgeBetween(Short value1, Short value2) {
            addCriterion("user_age between", value1, value2, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAgeNotBetween(Short value1, Short value2) {
            addCriterion("user_age not between", value1, value2, "userAge");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveIsNull() {
            addCriterion("user_attractive is null");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveIsNotNull() {
            addCriterion("user_attractive is not null");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveEqualTo(Short value) {
            addCriterion("user_attractive =", value, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveNotEqualTo(Short value) {
            addCriterion("user_attractive <>", value, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveGreaterThan(Short value) {
            addCriterion("user_attractive >", value, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveGreaterThanOrEqualTo(Short value) {
            addCriterion("user_attractive >=", value, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveLessThan(Short value) {
            addCriterion("user_attractive <", value, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveLessThanOrEqualTo(Short value) {
            addCriterion("user_attractive <=", value, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveIn(List<Short> values) {
            addCriterion("user_attractive in", values, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveNotIn(List<Short> values) {
            addCriterion("user_attractive not in", values, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveBetween(Short value1, Short value2) {
            addCriterion("user_attractive between", value1, value2, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andUserAttractiveNotBetween(Short value1, Short value2) {
            addCriterion("user_attractive not between", value1, value2, "userAttractive");
            return (Criteria) this;
        }

        public Criteria andEyeGlassIsNull() {
            addCriterion("eye_glass is null");
            return (Criteria) this;
        }

        public Criteria andEyeGlassIsNotNull() {
            addCriterion("eye_glass is not null");
            return (Criteria) this;
        }

        public Criteria andEyeGlassEqualTo(String value) {
            addCriterion("eye_glass =", value, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassNotEqualTo(String value) {
            addCriterion("eye_glass <>", value, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassGreaterThan(String value) {
            addCriterion("eye_glass >", value, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassGreaterThanOrEqualTo(String value) {
            addCriterion("eye_glass >=", value, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassLessThan(String value) {
            addCriterion("eye_glass <", value, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassLessThanOrEqualTo(String value) {
            addCriterion("eye_glass <=", value, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassLike(String value) {
            addCriterion("eye_glass like", value, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassNotLike(String value) {
            addCriterion("eye_glass not like", value, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassIn(List<String> values) {
            addCriterion("eye_glass in", values, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassNotIn(List<String> values) {
            addCriterion("eye_glass not in", values, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassBetween(String value1, String value2) {
            addCriterion("eye_glass between", value1, value2, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andEyeGlassNotBetween(String value1, String value2) {
            addCriterion("eye_glass not between", value1, value2, "eyeGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassIsNull() {
            addCriterion("sun_glass is null");
            return (Criteria) this;
        }

        public Criteria andSunGlassIsNotNull() {
            addCriterion("sun_glass is not null");
            return (Criteria) this;
        }

        public Criteria andSunGlassEqualTo(String value) {
            addCriterion("sun_glass =", value, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassNotEqualTo(String value) {
            addCriterion("sun_glass <>", value, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassGreaterThan(String value) {
            addCriterion("sun_glass >", value, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassGreaterThanOrEqualTo(String value) {
            addCriterion("sun_glass >=", value, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassLessThan(String value) {
            addCriterion("sun_glass <", value, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassLessThanOrEqualTo(String value) {
            addCriterion("sun_glass <=", value, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassLike(String value) {
            addCriterion("sun_glass like", value, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassNotLike(String value) {
            addCriterion("sun_glass not like", value, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassIn(List<String> values) {
            addCriterion("sun_glass in", values, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassNotIn(List<String> values) {
            addCriterion("sun_glass not in", values, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassBetween(String value1, String value2) {
            addCriterion("sun_glass between", value1, value2, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSunGlassNotBetween(String value1, String value2) {
            addCriterion("sun_glass not between", value1, value2, "sunGlass");
            return (Criteria) this;
        }

        public Criteria andSmileIsNull() {
            addCriterion("smile is null");
            return (Criteria) this;
        }

        public Criteria andSmileIsNotNull() {
            addCriterion("smile is not null");
            return (Criteria) this;
        }

        public Criteria andSmileEqualTo(Short value) {
            addCriterion("smile =", value, "smile");
            return (Criteria) this;
        }

        public Criteria andSmileNotEqualTo(Short value) {
            addCriterion("smile <>", value, "smile");
            return (Criteria) this;
        }

        public Criteria andSmileGreaterThan(Short value) {
            addCriterion("smile >", value, "smile");
            return (Criteria) this;
        }

        public Criteria andSmileGreaterThanOrEqualTo(Short value) {
            addCriterion("smile >=", value, "smile");
            return (Criteria) this;
        }

        public Criteria andSmileLessThan(Short value) {
            addCriterion("smile <", value, "smile");
            return (Criteria) this;
        }

        public Criteria andSmileLessThanOrEqualTo(Short value) {
            addCriterion("smile <=", value, "smile");
            return (Criteria) this;
        }

        public Criteria andSmileIn(List<Short> values) {
            addCriterion("smile in", values, "smile");
            return (Criteria) this;
        }

        public Criteria andSmileNotIn(List<Short> values) {
            addCriterion("smile not in", values, "smile");
            return (Criteria) this;
        }

        public Criteria andSmileBetween(Short value1, Short value2) {
            addCriterion("smile between", value1, value2, "smile");
            return (Criteria) this;
        }

        public Criteria andSmileNotBetween(Short value1, Short value2) {
            addCriterion("smile not between", value1, value2, "smile");
            return (Criteria) this;
        }

        public Criteria andEmotionIsNull() {
            addCriterion("emotion is null");
            return (Criteria) this;
        }

        public Criteria andEmotionIsNotNull() {
            addCriterion("emotion is not null");
            return (Criteria) this;
        }

        public Criteria andEmotionEqualTo(String value) {
            addCriterion("emotion =", value, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionNotEqualTo(String value) {
            addCriterion("emotion <>", value, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionGreaterThan(String value) {
            addCriterion("emotion >", value, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionGreaterThanOrEqualTo(String value) {
            addCriterion("emotion >=", value, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionLessThan(String value) {
            addCriterion("emotion <", value, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionLessThanOrEqualTo(String value) {
            addCriterion("emotion <=", value, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionLike(String value) {
            addCriterion("emotion like", value, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionNotLike(String value) {
            addCriterion("emotion not like", value, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionIn(List<String> values) {
            addCriterion("emotion in", values, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionNotIn(List<String> values) {
            addCriterion("emotion not in", values, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionBetween(String value1, String value2) {
            addCriterion("emotion between", value1, value2, "emotion");
            return (Criteria) this;
        }

        public Criteria andEmotionNotBetween(String value1, String value2) {
            addCriterion("emotion not between", value1, value2, "emotion");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(String value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(String value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(String value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(String value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(String value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(String value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLike(String value) {
            addCriterion("del_flag like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotLike(String value) {
            addCriterion("del_flag not like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<String> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<String> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(String value1, String value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(String value1, String value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
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