package org.rmysj.api.api.user.domain;

import org.rmysj.api.commons.domain.AbstractAuditingEntity;

import java.io.Serializable;

public class RobotUser extends AbstractAuditingEntity implements Serializable {

    private String userName;

    private String userAppell;

    private String userSex;

    private String userCard;

    private String userFace;

    private Short userAge;

    private Short userAttractive;

    private String eyeGlass;

    private String sunGlass;

    private Short smile;

    private String emotion;


    private static final long serialVersionUID = 1L;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserAppell() {
        return userAppell;
    }

    public void setUserAppell(String userAppell) {
        this.userAppell = userAppell == null ? null : userAppell.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard == null ? null : userCard.trim();
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace == null ? null : userFace.trim();
    }

    public Short getUserAge() {
        return userAge;
    }

    public void setUserAge(Short userAge) {
        this.userAge = userAge;
    }

    public Short getUserAttractive() {
        return userAttractive;
    }

    public void setUserAttractive(Short userAttractive) {
        this.userAttractive = userAttractive;
    }

    public String getEyeGlass() {
        return eyeGlass;
    }

    public void setEyeGlass(String eyeGlass) {
        this.eyeGlass = eyeGlass == null ? null : eyeGlass.trim();
    }

    public String getSunGlass() {
        return sunGlass;
    }

    public void setSunGlass(String sunGlass) {
        this.sunGlass = sunGlass == null ? null : sunGlass.trim();
    }

    public Short getSmile() {
        return smile;
    }

    public void setSmile(Short smile) {
        this.smile = smile;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion == null ? null : emotion.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(getId());
        sb.append(", userName=").append(userName);
        sb.append(", userAppell=").append(userAppell);
        sb.append(", userSex=").append(userSex);
        sb.append(", userCard=").append(userCard);
        sb.append(", userFace=").append(userFace);
        sb.append(", userAge=").append(userAge);
        sb.append(", userAttractive=").append(userAttractive);
        sb.append(", eyeGlass=").append(eyeGlass);
        sb.append(", sunGlass=").append(sunGlass);
        sb.append(", smile=").append(smile);
        sb.append(", emotion=").append(emotion);
        sb.append(", createBy=").append(getCreateBy());
        sb.append(", createDate=").append(getCreateDate());
        sb.append(", updateBy=").append(getUpdateBy());
        sb.append(", updateDate=").append(getUpdateDate());
        sb.append(", remarks=").append(getRemarks());
        sb.append(", delFlag=").append(getDelFlag());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}