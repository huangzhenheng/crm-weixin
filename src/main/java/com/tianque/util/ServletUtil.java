package com.tianque.util;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {

    /**
     * 获取客户端IP地址
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}