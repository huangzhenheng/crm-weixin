package com.tianque.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tianque.dto.FlashMessage;
import com.tianque.pojo.User;
import com.tianque.service.UserService;
import com.tianque.util.ServletUtil;

@Controller
public class HomeController {

    @Inject
    private UserService userService;

    /**
     * 去登录页面
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(String username, String password,
                        RedirectAttributes redirectAttributes,
                        HttpServletRequest request) {
        //获取认证主体，如果主体已存在，则将当前的主体退出
        Subject currentUser = SecurityUtils.getSubject();

        if(currentUser.isAuthenticated()) {
            //当前用户已经登录,则先退出之前的账号
            currentUser.logout();
        }

        //判断当前用户
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

            currentUser.login(token); //调用subject.login方法进行登录，其会自动委托给SecurityManager.login方法进行登录；
            //调用Subject.login进行登录，如果失败将得到相应的AuthenticationException异常，根据异常提示用户错误信息；否则登录成功
            //将登录的对象放入到Session中
            Session session = currentUser.getSession();
            session.setAttribute(User.SESSION_KEY,currentUser.getPrincipal());
            //获取登录的IP地址，并保存用户登录的日志
            userService.saveUserLogin(ServletUtil.getRemoteIp(request));

            return "redirect:/home";
        } catch (LockedAccountException ex) {
            redirectAttributes.addFlashAttribute("message",new FlashMessage(FlashMessage.STATE_ERROR,"账号已被禁用"));
        } catch (AuthenticationException exception) {
            redirectAttributes.addFlashAttribute("message",new FlashMessage(FlashMessage.STATE_ERROR,"账号或密码错误"));
        }
        return "redirect:/";

    }

    /**
     * 若登录成功去home页
     */
    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * 安全退出
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes) {
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message",new FlashMessage("你已安全退出"));
        return "redirect:/";
    }


    @RequestMapping("/403")
    public String noPower() {
        return "error/403";
    }

}
