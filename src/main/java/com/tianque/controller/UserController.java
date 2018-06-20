package com.tianque.controller;


import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianque.dto.DataTablesResult;
import com.tianque.exception.NotFoundException;
import com.tianque.pojo.User;
import com.tianque.pojo.UserLog;
import com.tianque.service.UserService;
import com.tianque.util.ShiroUtil;

@Controller
@RequestMapping("/user")
public class UserController {
    @Inject
    private UserService userService;


    /**
     * 去修改密码页
     */
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String editPassword() {

        return "setting/password";
    }


    /**
     * 保存修改的密码
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public String editPassword(String password) {
        userService.changePassword(password);
        return "success";
    }

    /**
     * 验证原始密码是否正确(Ajax调用)
     */
    @RequestMapping(value = "/validate/password", method = RequestMethod.GET)
    @ResponseBody
    public String validateOldPassword(@RequestHeader("X-Requested-With") String xRequestedWith,
                                      String oldpassword) {
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            User user = ShiroUtil.getCurrentUser();
            if (user.getPassword().equals(oldpassword)) {
                return "true";
            }
            return "false";
        } else {
            throw new NotFoundException();
        }
    }


    /**
     * 显示当前登录用户的登录日志
     */
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public String showUserLog() {
        return "setting/loglist";
    }

    /**
     * 使用DataTables显示数据
     */
    @RequestMapping(value = "/log/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult userLogLoad(HttpServletRequest request) {

        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        List<UserLog> userLogList = userService.findCurrentUserLog(start, length);
        Long count = userService.findCurrentUserLogCount();

        return new DataTablesResult<>(draw, userLogList, count, count);
    }

}
