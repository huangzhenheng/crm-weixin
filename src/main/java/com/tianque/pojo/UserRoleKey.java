package com.tianque.pojo;

import java.io.Serializable;

public class UserRoleKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userid;

	private Integer roleid;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

}
