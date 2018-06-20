<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
    <title>CRM|403</title>
	<%@ include file="../include/includeCSS.jsp" %>

</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/mainHeader.jsp"%>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="home"/>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="font-family: 宋体">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                403:您未授权......       <button class="btn btn-success" id="back">返回</button>
            </h1>

        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Your Page Content Here -->

        </section>
        <!-- /.content -->
    </div>
    <!-- Control Sidebar -->

</div>

<%@ include file="../include/includeJS.jsp" %>
<script>

    $(function(){
        $("#back").click(function(){
            window.history.go(-1);
        });

    });
</script>
</body>
</html>
