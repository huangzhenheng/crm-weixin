package com.tianque.pojo;

public class UserLog extends BasePojo {

    private static final long serialVersionUID = 6790669625190853675L;

    private String logintime;
    private String loginip;
    private Integer userid;

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

}
