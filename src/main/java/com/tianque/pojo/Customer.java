package com.tianque.pojo;

import java.sql.Timestamp;

public class Customer extends BasePojo {
    private static final long serialVersionUID = 8079486369524898977L;

    public static final String CUSTOMER_TYPE_PERSON = "person";
    public static final String CUSTOMER_TYPE_COMPANY = "company";

    private String custname;
    private String tel;
    private String weixin;
    private String address;
    private String email;
    private Timestamp createtime;
	private User createUser;
    private String pinyin;
    private Integer companyid;
    private String level;
    private String remark;
    private String type;


    public Customer() {}



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }



    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

}
