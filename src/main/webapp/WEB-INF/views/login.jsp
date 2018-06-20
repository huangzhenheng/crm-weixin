<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>CRM|登录</title>
    <%@ include file="include/includeCSS.jsp" %>
</head>
<body class="hold-transition login-page" style="background-image: url(/static/img/003.jpg)">
<div class="login-box">
    <div class="login-logo">
        <a href="/"><b>CRM</b></a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">

        <c:if test="${not empty message}">
            <c:choose>
                <c:when test="${message.state == 'success'}">
                    <div class="alert alert-success">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                            ${message.message}
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                            ${message.message}
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>
        <form action="/" method="post" id="loginForm">
            <div class="form-group has-feedback">
                <input autofocus type="text" class="form-control" placeholder="账号" name="username" id="username">
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" placeholder="密码" name="password" id="password">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input name="rememberMe" type="checkbox" value="true"> 记住我
                        </label>
                    </div>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="submit" id="login" class="btn btn-primary btn-block btn-flat">登录</button>
                </div>
                <!-- /.col -->
            </div>
        </form>

        <a href="#">忘记密码？</a><br>

    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<%@ include file="include/includeJS.jsp" %>
<script>
    $(function () {
    	
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });


    });
</script>
</body>
</html>
