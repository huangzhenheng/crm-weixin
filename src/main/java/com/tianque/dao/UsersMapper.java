package com.tianque.dao;

import java.util.List;
import java.util.Map;

import com.tianque.pojo.Role;
import com.tianque.pojo.User;
import com.tianque.pojo.UserRoleKey;

public interface UsersMapper {

	User findByUsername(String username);

	void updateUser(User user);

	List<User> findAll();

	Long count();

	List<User> findByDataTable(Map<String, Object> param);

	void save(User user);

	User findUserById(Integer id);

	void update(User user);

	Long countByParam(Map<String, Object> param);

	List<Role> findRolesByUserid(Integer id);

	void insert(UserRoleKey userRoleKey);

}
