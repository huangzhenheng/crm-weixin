package com.tianque.dao;

import java.util.List;

import com.tianque.pojo.Role;

public interface RoleMapper {

    Role findById(Integer roleId);

    List<Role> findAllRole();
}
