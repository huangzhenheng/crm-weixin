package com.tianque.util;

import org.apache.shiro.SecurityUtils;

import com.tianque.pojo.User;

public class ShiroUtil {

    /**
     * 判断当前是否登录状态
     * @return
     */
    public static boolean isCurrentUser(){
        return getCurrentUser() != null;
    }


    public static User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

	public static Integer getCurrentUserID() {
        return getCurrentUser().getId();
    }

    public static String getCurrentUserName() {
        return getCurrentUser().getUsername();
    }

	public static Integer getCurrentRoleId() {
		return null;
		// return getCurrentUser().getRoleid();
	}

	public static String getCurrentRealName() {
		return getCurrentUser().getRealname();
	}

	public static boolean isAdmin() {
		return true;
		// return getCurrentUser().getRole().getRolename().equals("管理员");
	}

	public static boolean isEmployee() {
		return true;
		// return getCurrentUser().getRole().getRolename().equals("员工");
	}

	public static boolean isManager() {
		return true;
		// return getCurrentUser().getRole().getRolename().equals("经理");
	}
}