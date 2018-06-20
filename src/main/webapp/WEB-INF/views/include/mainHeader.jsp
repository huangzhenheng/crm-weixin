<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- Main Header -->
<header class="main-header">

    <!-- Logo -->
    <a href="/home" class="logo">
        <span class="logo-mini"><b>CRM</b></span>

        <span class="logo-lg">CRM</span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="/static/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                        <span class="hidden-xs"><shiro:principal property="realname"/></span>
                    </a>
                    <ul class="dropdown-menu" style="width:auto">
                        <li class="user-body">
                            <a href="/user/password">修改密码</a>
                        </li>
                        <li class="user-body">
                            <a href="/user/log">登录日志</a>
                        </li>
                        <li class="user-body">
                            <a href="/logout">安全退出</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>