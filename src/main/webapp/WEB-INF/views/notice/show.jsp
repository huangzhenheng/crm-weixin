<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
<head>
    <title>CRM|查看公告</title>
    <%@ include file="../include/includeCSS.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/mainHeader.jsp" %>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="notice"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="font-family: 宋体">
        <!-- Main content -->
        <section class="content">
            <c:if test="${not empty message}">
                <div class="alert alert-success" id="hide">${message}</div>
            </c:if>
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title"><a href="/notice"><i class="fa fa-list"></i> 公告列表&nbsp;>>&nbsp;</a>${notice.title}</h3>
                </div>
                <div class="box-body">
                    <h2 class="text-center">${notice.title}</h2>
                    <div style="text-align:center;line-height:40px">[<fmt:formatDate value="${notice.createtime}" pattern="y-M-d H:m"/>]
                        <span style="line-height: 200%; font-size: 12pt">发表人：${notice.realname}</span>
                    </div>
                    <div>
                        ${notice.context}
                    </div>
                    <div class="text-center">
                        <hr>
                        <a href="/notice"><button class="btn btn-xs btn-success"><i class="fa  fa-level-up"></i>返回</button>&nbsp;</a>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <!-- /.content -->
</div>

<%@ include file="../include/includeJS.jsp" %>
<script>
    $(function () {
        var edit = new Simditor({
            textarea: $("#context")
        });
        $("#saveBtn").click(function () {
            if (!$("#title").val()) {
                $("#title").focus();
                return;
            }
            if (!$("#context").val()) {
                $("#context").focus();
                return;
            }
            $("#newForm").submit();
        });

    });
</script>
</body>
</html>
