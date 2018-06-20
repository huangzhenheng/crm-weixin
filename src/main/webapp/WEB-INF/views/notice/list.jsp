<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
    <title>CRM|公告列表</title>
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
                <div class="alert alert-success" id="msg">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                        ${message}
                </div>
            </c:if>
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">公告列表</h3>
                    <shiro:hasRole name="经理">
                        <div class="box-tools pull-right">
                            <a href="/notice/new" class="btn btn-xs btn-success"><i class="fa fa-pencil"></i> 发表公告</a>
                        </div>
                    </shiro:hasRole>
                </div>
                <div class="box-body">
                    <table class="table" id="noticeTable">
                        <thead>
                        <tr>
                            <th>标题</th>
                            <th>发布时间</th>
                            <th>发布人</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </section>
    </div>
    <!-- Control Sidebar -->

</div>

<%@ include file="../include/includeJS.jsp" %>
<script>
    $(function(){
        var dataTable = $("#noticeTable").DataTable({
            searching:false,
            serverSide:true,
            ajax:"/notice/load",
            ordering:false,
            "autoWidth": false,
            columns:[
                {"data":function(row){
                    return "<a href='/notice/show/"+row.id+"'>"+row.title+"</a>"
                }},
                {"data":function(row){
                    var day = moment(row.createtime).format("YYYY-MM-DD HH:mm");
                    return day;
                }},
                {"data":"realname"}
            ],
            "language": { //定义中文
                "search": "请输入书籍名称:",
                "zeroRecords": "没有匹配的数据",
                "lengthMenu": "显示 _MENU_ 条数据",
                "info": "显示从 _START_ 到 _END_ 条数据 共 _TOTAL_ 条数据",
                "infoFiltered": "(从 _MAX_ 条数据中过滤得来)",
                "loadingRecords": "加载中...",
                "processing": "处理中...",
                "paginate": {
                    "first": "首页",
                    "last": "末页",
                    "next": "下一页",
                    "previous": "上一页"
                }
            }
        });
    });
</script>
</body>
</html>
