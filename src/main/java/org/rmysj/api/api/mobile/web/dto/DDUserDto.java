package org.rmysj.api.api.mobile.web.dto;

import java.io.Serializable;

public class DDUserDto implements Serializable {

    public DDUserDto(){
        super();
    }

    private  String ddUserId;
    private String ddUserName;
    private String ddUsercode;

    public String getDdUserId() {
        return ddUserId;
    }

    public void setDdUserId(String ddUserId) {
        this.ddUserId = ddUserId;
    }

    public String getDdUserName() {
        return ddUserName;
    }

    public void setDdUserName(String ddUserName) {
        this.ddUserName = ddUserName;
    }

    public String getDdUsercode() {
        return ddUsercode;
    }

    public void setDdUsercode(String ddUsercode) {
        this.ddUsercode = ddUsercode;
    }
}
