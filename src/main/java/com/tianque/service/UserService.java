package com.tianque.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.common.collect.Maps;
import com.tianque.dao.UserLogMapper;
import com.tianque.dao.UsersMapper;
import com.tianque.pojo.Role;
import com.tianque.pojo.User;
import com.tianque.pojo.UserLog;
import com.tianque.pojo.UserRoleKey;
import com.tianque.util.ShiroUtil;

@Named
public class UserService {

	@Inject
	private UsersMapper usersMapper;
	@Inject
	private UserLogMapper userLogMapper;

	/**
	 * 创建用户的登录日志
	 */
	public void saveUserLogin(String ip) {
		UserLog userLog = new UserLog();
		userLog.setLoginip(ip);
		userLog.setLogintime(DateTime.now().toString("yyyy-MM-dd HH:mm"));
		userLog.setUserid(ShiroUtil.getCurrentUserID());

		userLogMapper.saveUserLog(userLog);
	}

	/**
	 * 通过username查找某个用户
	 */
	public User findUserByName(String username) {

		return usersMapper.findByUsername(username);
	}

	/**
	 * 获取当前登录用户的登录日志
	 */
	public List<UserLog> findCurrentUserLog(String start, String length) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("userId", ShiroUtil.getCurrentUserID());
		param.put("start", start);
		param.put("length", length);
		return userLogMapper.findByParam(param);
	}

	/**
	 * 获取当前登录用户的日志数量
	 */
	public Long findCurrentUserLogCount() {
		Map<String, Object> param = Maps.newHashMap();
		param.put("userId", ShiroUtil.getCurrentUserID());
		return userLogMapper.countByParam(param);
	}

	/**
	 * 修改用户密码
	 */
	public void changePassword(String password) {

		User user = ShiroUtil.getCurrentUser();
		user.setPassword(password);

		usersMapper.updateUser(user);
	}

	public List<User> findAllUsers() {
		return usersMapper.findAll();
	}

	public Long count() {
		return usersMapper.count();
	}

	public List<User> findByDataTables(Map<String, Object> param) {

		return usersMapper.findByDataTable(param);
	}

	public List<User> findAllUser(Map<String, Object> param) {
		return usersMapper.findByDataTable(param);
	}

	public void saveUser(User user) {
		usersMapper.save(user);
	}

	public User findUserById(Integer id) {
		return usersMapper.findUserById(id);
	}

	public void updateUser(User user) {
		usersMapper.update(user);
	}

	public Long findUserCountByParam(Map<String, Object> param) {
		return usersMapper.countByParam(param);
	}

	public void resetPw(Integer id) {
		User user = usersMapper.findUserById(id);
		if (user != null) {
			user.setPassword("000000");
			usersMapper.updateUser(user);
		}
	}

	public void saveUser(User user, Integer[] roleIds) {
		usersMapper.save(user);

		// 3.添加账号和部门的关系
		for (Integer roleid : roleIds) {
			UserRoleKey userRoleKey = new UserRoleKey();
			userRoleKey.setRoleid(roleid);
			userRoleKey.setUserid(user.getId());
			usersMapper.insert(userRoleKey);
		}
	}

	public List<Role> findRolesByUserid(Integer id) {
		return usersMapper.findRolesByUserid(id);
	}
}
