<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <title>CRM|客户信息</title>
   <%@ include file="../include/includeCSS.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/mainHeader.jsp" %>
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="customer"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <section class="content-header">
            <button id="comeBack" class="btn btn-xs btn-success">返回</button>
            <ol class="breadcrumb">
                <li><a href="/customer"><i class="fa fa-dashboard"></i> 客户列表</a></li>
                <li class="active">${customer.custname}</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">
                        <c:choose>
                            <c:when test="${customer.type == 'person'}">
                                <i class="fa fa-user text-success"></i>
                            </c:when>
                            <c:otherwise>
                                <i class="fa fa-bank text-success"></i>
                            </c:otherwise>
                        </c:choose>
                        <span class="text-success">${customer.custname}</span>
                    </h3>
                    <div class="box-tools">
                        <c:if test="${not empty customer.createUser}">

                            <button class="btn btn-danger btn-xs" id="openCust">公开客户</button>

                            <button class="btn btn-success btn-xs" id="moveCust">转移客户</button>
                        </c:if>

                        <c:if test="${empty customer.createUser  and (roleid eq 2)}">
                            <button class="btn btn-success btn-xs" id="moveCust">指定负责人</button>
                        </c:if>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <td style="width: 100px">联系电话:</td>
                            <td style="width: 200px">${customer.tel}</td>
                            <td style="width: 100px">微信:</td>
                            <td style="width: 200px">${customer.weixin}</td>
                            <td style="width: 100px">电子邮件:</td>
                            <td>${customer.email}</td>
                        </tr>
                        <tr>
                            <td>等级:</td>
                            <td style="color: #FFD700">${customer.level}</td>
                            <td>地址:</td>
                            <td colspan="3">${customer.address}</td>
                        </tr>
                        <tr>
                            <td>负责人:</td>
                            <td>${user.realname}</td>
                            <td>备注:</td>
                            <td colspan="3">${customer.remark}</td>
                        </tr>
                        <c:if test="${not empty customer.companyid}">
                            <tr>
                                <td>所属公司:</td>
                                <td colspan="5"><a href="/customer/${customer.companyid}">${customer.companyname}</a>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty customerList}">
                            <tr>
                                <td>关联客户:</td>
                                <td colspan="5">
                                    <c:forEach items="${customerList}" var="cust">
                                        <a href="/customer/${cust.id}">${cust.custname}</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </div>
            </div>
            <%--customer box end--%>
            <div class="row">
                <div class="col-md-8">
                    <div class="box box-info">

                        <div class="box-header">
                            <h3 class="box-title"><i class="fa fa-list"></i> 机会列表</h3>
                        </div>

                        <div class="box-body">
                            <table class="table">
                                <c:if test="${empty saleList}">
                                    <tr>
                                        <td rowspan="4"><h5>暂无相关机会</h5></td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty saleList}">
                                    <tr>
                                        <th>机会名称</th>
                                        <th>价值</th>
                                        <th style="width: 130px">当前进度</th>
                                        <th style="width: 130px">最后跟进时间</th>
                                    </tr>
                                </c:if>
                                <tr>
                                    <c:forEach var="sale" items="${saleList}">
                                    <td><a href="/sales/${sale.id}">${sale.name}</a></td>
                                    <td>${sale.price}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${sale.progress == '完成交易'}">
                                                <span class="label label-success">${sale.progress}</span>
                                            </c:when>
                                            <c:when test="${sale.progress == '交易搁置'}">
                                                <span class="label label-danger">${sale.progress}</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${sale.progress}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${sale.lasttime}</td>
                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>

                <%--col-md-8 end--%>
                <div class="col-md-4">
                    <div class="box box-primary collapsed-box">
                        <div class="box-header">
                            <h3 class="box-title"><i class="fa fa-qrcode"></i> 电子名片</h3>
                            <div class="box-tools">
                                <button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"><i
                                        class="fa fa-plus"></i></button>
                            </div>
                        </div>
                        <div class="box-body" style="text-align: center">
                            <img src="/customer/qrcode/${customer.id}.png">
                        </div>
                    </div>

                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-calendar-check-o"></i> 代办事项</h3>
                            <div class="box-tools">
                                <button class="btn btn-default btn-xs" id="newTask"><i class="fa fa-plus"></i></button>
                            </div>
                        </div>
                        <div class="box-body">
                            <c:if test="${empty taskList}">
                                <h5>暂无代办事项</h5>
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
                <%--col-md-4 end--%>
            </div>


        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->
<div class="modal fade" id="moveModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">转移客户</h4>
            </div>

            <div class="modal-body">
                <form id="moveForm" action="/customer/move" method="post">
                    <input type="hidden" name="id" value="${customer.id}">
                    <div class="form-group" id="editCompanyList">
                        <label>请选择接收者</label>
                        <select name="userid" class="form-control">
<%--                             <c:forEach items="${userList}" var="user"> --%>
<%--                                 <c:if test="${user.roleid eq 3}"> --%>
<%--                                     <option value="${user.id}">${user.realname}</option> --%>
<%--                                 </c:if> --%>
<%--                             </c:forEach> --%>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="moveBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div class="modal fade" id="newTaskModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增待办事项</h4>
            </div>
            <div class="modal-body">

                <form id="newTaskForm" action="/customer/task/new" method="post">
                    <input type="hidden" name="custid" value="${customer.id}">
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
                                    <option value="5">5</option>
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
</div><!-- /.modal -->
<%@ include file="../include/includeJS.jsp" %>
<script>
    $(function () {


        $("#openCust").click(function () {
            var id = ${customer.id};
            if (confirm("确定要公开客户吗？")) {
                window.location.href = "/customer/open/" + id;
            }
        });

        $("#moveCust").click(function () {
            $("#moveModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });

        $("#moveBtn").click(function () {
            $("#moveForm").submit();
        });

        $("#comeBack").click(function () {
            window.history.go(-1);
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