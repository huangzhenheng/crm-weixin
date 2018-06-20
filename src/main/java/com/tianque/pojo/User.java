package com.tianque.pojo;

import java.sql.Timestamp;
import java.util.List;

public class User extends BasePojo {

    private static final long serialVersionUID = -2106420694147670781L;
    public static final String SESSION_KEY = "curr_user";

    private String username;
    private String password;
    private String realname;
    private String weixin;
    private Timestamp createtime;
    private Boolean status;
	private String email;
	private String pinyin;
	private List<Role> roleList;
	private String userid;
	private String tel;

	public User() {
	}

	public User(String username, String tel, String userid) {
		this.username = username;
		this.tel = tel;
		this.userid = userid;
	}

	public User(Integer id) {
		super.setId(id);
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userId) {
		this.userid = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}


}
