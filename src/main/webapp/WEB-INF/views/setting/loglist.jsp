<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
   <title>CRM|登录日志</title>
    <%@ include file="../include/includeCSS.jsp" %>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/mainHeader.jsp"%>
    <%@include file="../include/leftSide.jsp"%>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">登录日志列表</h3>
                        </div>
                        <div class="box-body">
                            <table class="table table-bordered table-hover" id="logTable">
                                <thead>
                                <tr>
                                    <th>登录时间</th>
                                    <th>登录IP</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>

<%@ include file="../include/includeJS.jsp" %>
<script>
    $(function(){
        var dateTable = $("#logTable").DataTable({
            "lengthMenu": [5, 10, 25, 50],
            searching:false,
            serverSide:true,
            ajax:"/user/log/load",
            ordering:false,
            "autoWidth": false,
            columns:[
                {"data":"logintime"},
                {"data":"loginip"}
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