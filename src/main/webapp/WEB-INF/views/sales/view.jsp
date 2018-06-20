<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>CRM | 销售机会 | ${sale.name}</title>
    <%@ include file="../include/includeCSS.jsp" %>
    <style>
        .timeline > li > .timeline-item {
            box-shadow: none;
            -webkit-box-shadow: none;
        }

        .files li {
            padding: 5px;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/mainHeader.jsp" %>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="sales"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <button id="comeBack" class="btn btn-xs btn-success">返回</button>
            <ol class="breadcrumb">
                <li><a href="/sales"><i class="fa fa-dashboard"></i> 机会列表</a></li>
                <li class="active">${sale.name}</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <%--销售信息--%>
            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title"><i class="fa fa-caret-right"></i> ${sale.name}</h3>
                    <shiro:hasRole name="经理">
                        <div class="box-tools">
                            <button class="btn btn-danger btn-xs" id="delBtn"><i class="fa fa-trash"></i> 删除</button>
                        </div>
                    </shiro:hasRole>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td style="width: 100px">关联客户：</td>
                            <td style="width: 200px">
                                <c:if test="${not empty sale.type}">
                                    <c:choose>
                                        <c:when test="${sale.type == 'person'}">
                                            <a href="/customer/${sale.custid}"><i
                                                    class='fa fa-user'></i> ${sale.custname}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="/customer/${sale.custid}"><i
                                                    class='fa fa-bank'></i> ${sale.custname}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td style="width: 100px">价值：</td>
                            <td style="width: 200px"><b>￥</b><fmt:formatNumber value="${sale.price}"/> </td>
                        </tr>
                        <tr>
                            <td>当前进度：</td>
                            <td>
                                <c:if test="${not empty sale.progress}">
                                    <c:choose>
                                        <c:when test="${sale.progress == '交易搁置'}">
                                            <span class="label label-danger">${sale.progress}</span>
                                        </c:when>
                                        <c:when test="${sale.progress == '完成交易'}">
                                            <span class="label label-success">${sale.progress}</span>
                                        </c:when>
                                        <c:otherwise>
                                            ${sale.progress}
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                &nbsp;&nbsp;<a href="#">
                                <button class="btn btn-xs btn-success" id="editBtn">修改</button>
                            </a>
                            </td>
                            <td>最后跟进时间：</td>
                            <td>${empty sale.lasttime ? '无' : sale.lasttime}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="row">

                <%--跟进记录--%>
                <div class="col-md-8">
                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h3 class="box-title">跟进记录</h3>
                            <div class="box-tools">
                                <button id="newSalelog" class="btn btn-xs btn-success"><i class="fa fa-plus"></i> 新增记录</button>
                            </div>
                        </div>
                        <div class="box-body">
                            <ul class="timeline">
                                <c:forEach items="${saleLogList}" var="log">
                                    <li>
                                        ${log.type == 'auto' ? '<i class="fa fa-history bg-yellow"></i>' : '<i class="fa fa-commenting bg-aqua"></i>'}
                                        <div class="timeline-item">
                                            <span class="time"><i class="fa fa-clock-o"></i> <span class="timeago" title="${log.createtime}"></span></span>
                                            <h3 class="timeline-header no-border">
                                                    ${log.context}
                                            </h3>
                                        </div>
                                    </li>
                                </c:forEach>
                                <li>
                                    <i class="fa fa-clock-o bg-gray"></i>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">

                    <%--该销售机会的相关资料--%>
                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-file-o"></i> 相关资料</h3>
                            <div class="box-tools">
                                <span id="picker"><span class="text btn btn-xs btn-default"><i class="fa fa-upload"></i></span></span>
                            </div>
                        </div>
                        <div class="box-body">

                            <c:if test="${empty saleFileList}">
                                <h5>暂无相关文档</h5>
                            </c:if>
                            <ul class="list-unstyled files">
                                <c:forEach var="doc" items="${saleFileList}">
                                    <li><a href="/sales/download/${doc.id}">${doc.name}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <%--该销售机会的待办事项--%>
                        <div class="box box-default">
                            <div class="box-header with-border">
                                <h3 class="box-title"><i class="fa fa-calendar-check-o"></i> 代办任务</h3>
                                <div class="box-tools">
                                    <button class="btn btn-default btn-xs" id="newTask"><i class="fa fa-plus"></i></button>
                                </div>
                            </div>
                            <div class="box-body">
                                <c:if test="${empty taskList}">
                                    <h5>暂无代办任务</h5>
                                </c:if>
                                <ul class="todo-list">
                                    <c:forEach var="task" items="${taskList}">
                                        <li> <a href="/task"><span class="text">${task.title}</span></a>
                                            <i class="fa fa-tag pull-right" style="color: #00c0ef">&nbsp;&nbsp;&nbsp;</i></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->
<div class="modal fade" id="progressModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改当前进度</h4>
            </div>
            <div class="modal-body">
                <form id="progressForm" action="/sales/progress" method="post">
                    <input type="hidden" name="id" value="${sale.id}">
                    <div class="form-group" id="editProgressList">
                        <select name="progress" class="form-control" class="form-control" id="s_progress">
                            <option value="${sale.progress}">${sale.progress}</option>
                            <c:if test="${sale.progress != '初次接触'}">
                                <option value="初次接触">初次接触</option>
                            </c:if>
                            <c:if test="${sale.progress != '发送报价'}">
                                <option value="发送报价">发送报价</option>
                            </c:if>
                            <c:if test="${sale.progress != '确认意向'}">
                                <option value="确认意向">确认意向</option>
                            </c:if>
                            <c:if test="${sale.progress != '提供合同'}">
                                <option value="提供合同">提供合同</option>
                            </c:if>
                            <c:if test="${sale.progress != '完成交易'}">
                                <option value="完成交易">完成交易</option>
                            </c:if>
                            <c:if test="${sale.progress != '交易搁置'}">
                                <option value="交易搁置">交易搁置</option>
                            </c:if>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- REQUIRED JS SCRIPTS -->
<%--新增跟进记录--%>
<div class="modal fade" id="newLogModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增跟进记录</h4>
            </div>
            <div class="modal-body">
                <form id="newLogForm" action="/sales/log/new" method="post">
                    <input type="hidden" name="salesid" value="${sale.id}">
                    <div class="form-group">
                        <textarea name="context" id="context"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveLogBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<div class="modal fade" id="newTaskModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增待办事项</h4>
            </div>
            <div class="modal-body">

                <form id="newTaskForm" action="/sales/task/new" method="post">
                    <input type="hidden" name="salesid" value="${sale.id}">
                    <div class="form-group">
                        <p>日程内容：<input type="text" class="input" name="title" id="task_title" style="width:320px"
                                       placeholder="记录你将要做的一件事..."></p>
                    </div>
                    <%--开始时间--%>
                    <div class="form-group">
                        <p>开始时间：<input type="text" name="start" id="start_time"></p>
                    </div>
                    <%--结束时间--%>
                    <div class="form-group">
                        <p id="p_endtime">结束时间：<input type="text" name="end" id="end_time"></p>
                    </div>
                    <%--提醒时间--%>
                    <div class="form-group">
                        <div>
                            <p>提醒时间：<select name="hour">
                                <option value=""></option>
                                <option value="00">00</option>
                                <option value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                            </select>
                                <b>:</b>
                                <select name="min">
                                    <option value=""></option>
                                    <option value="00">00</option>
                                    <option value="05">05</option>
                                    <option value="10">10</option>
                                    <option value="15">15</option>
                                    <option value="20">20</option>
                                    <option value="25">25</option>
                                    <option value="30">30</option>
                                    <option value="35">35</option>
                                    <option value="40">40</option>
                                    <option value="45">45</option>
                                    <option value="50">50</option>
                                    <option value="55">55</option>
                                </select>
                            </p>
                        </div>
                    </div>
                    <div class="form-group">
                        <p>显示颜色：<input type="text" name="color" id="color" value="#61a5e8"></p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveTaskBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<%@ include file="../include/includeJS.jsp" %>
<script>
    $(function () {
        //相对时间
        $(".timeago").timeago();

        $("#comeBack").click(function () {
            window.history.go(-1);
        });

        <shiro:hasRole name="经理">
        $("#delBtn").click(function () {
            if (confirm("确定要删除销售机会吗？")) {
                var id = ${sale.id};
                window.location.href = "/sales/del/" + id;
            }
        });
        </shiro:hasRole>


        $("#editBtn").click(function () {
            $("#progressModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });

        $("#saveBtn").click(function () {
            $("#progressForm").submit();
        });

        var uploader = WebUploader.create({
            swf: "/static/plugins/webuploader/Uploader.swf",
            server: "/sales/saveSaleFile",
            pick: "#picker",
            fileVal: "file",
            formData:{"saleid":"${sale.id}"},
            auto:true
        });

        uploader.on( 'uploadSuccess', function( file,data ) {
            if(data._raw == "success") {
                window.history.go(0);
            }
        });

        uploader.on( 'uploadError', function( file ) {
            alert("文件上传失败");
        });


        $("#newSalelog").click(function(){

            $("#newLogModal").modal({
                show:true,
                backdrop:'static'
            });
        });
        //在线编辑器
        var edit = new Simditor({
            textarea:$("#context"),
            placeholder: '请输入跟进内容',
            toolbar:false
        });

        $("#saveLogBtn").click(function(){

            if(!$("#context").val()) {
                $("#context").focus();
                return;
            }

            $("#newLogForm").submit();
        });



        $("#color").colorpicker({
            color:'#61a5e8'
        });
        $("#start_time,#end_time").datepicker({
            format: 'yyyy-mm-dd',
            autoclose:true,
            language:'zh-CN',
            todayHighlight:true
        });
        //新建任务
        $("#newTask").click(function(){
            $("#newTaskModal").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#saveTaskBtn").click(function(){
            $("#newTaskForm").submit();
        });

    });
</script>
</body>
</html>
