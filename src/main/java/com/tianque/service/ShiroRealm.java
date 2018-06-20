package com.tianque.service;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.tianque.dao.RoleMapper;
import com.tianque.pojo.Role;
import com.tianque.pojo.User;

@Named
public class ShiroRealm extends AuthorizingRealm {

    @Inject
    private UserService userService;
    @Inject
    private RoleMapper roleMapper;

    /**
     * 权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		// 获取当前登录的对象
        User user = (User) principalCollection.getPrimaryPrincipal();

        if (user != null) {
        	
        	// 根据登录的对象获取角色列表
    		List<Role> roleList = userService.findRolesByUserid(user.getId());
    		// 获取Role集合中的名称，创建字符串列表
    		List<String> roleNameList = new ArrayList<>();
    		for (Role role : roleList) {
    			roleNameList.add(role.getRolename());
    		}
    		
    		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    		// 将部门名称作为当前用户的角色
    		simpleAuthorizationInfo.addRoles(roleNameList);
          
            return simpleAuthorizationInfo;
        }
        return null;


    }


    /**
     * 用于验证登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername(); //获取用户表单中的账号
        User user = userService.findUserByName(username); //根据账号查找对应的对象

        if (user != null) {
            //判断账号是否被禁用
            if (!user.getStatus()) {
                throw new LockedAccountException("账号已被禁用");
            }
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        } else {
            throw new UnknownAccountException("账号或密码错误");
        }


    }
}
